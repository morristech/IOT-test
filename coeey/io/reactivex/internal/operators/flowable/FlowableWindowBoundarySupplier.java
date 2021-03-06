package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.SimplePlainQueue;
import io.reactivex.internal.queue.MpscLinkedQueue;
import io.reactivex.internal.subscribers.QueueDrainSubscriber;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.NotificationLite;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.processors.UnicastProcessor;
import io.reactivex.subscribers.DisposableSubscriber;
import io.reactivex.subscribers.SerializedSubscriber;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableWindowBoundarySupplier<T, B> extends AbstractFlowableWithUpstream<T, Flowable<T>> {
    final int bufferSize;
    final Callable<? extends Publisher<B>> other;

    static final class WindowBoundaryInnerSubscriber<T, B> extends DisposableSubscriber<B> {
        boolean done;
        final WindowBoundaryMainSubscriber<T, B> parent;

        WindowBoundaryInnerSubscriber(WindowBoundaryMainSubscriber<T, B> parent) {
            this.parent = parent;
        }

        public void onNext(B b) {
            if (!this.done) {
                this.done = true;
                cancel();
                this.parent.next();
            }
        }

        public void onError(Throwable t) {
            if (this.done) {
                RxJavaPlugins.onError(t);
                return;
            }
            this.done = true;
            this.parent.onError(t);
        }

        public void onComplete() {
            if (!this.done) {
                this.done = true;
                this.parent.onComplete();
            }
        }
    }

    static final class WindowBoundaryMainSubscriber<T, B> extends QueueDrainSubscriber<T, Object, Flowable<T>> implements Subscription {
        static final Object NEXT = new Object();
        final AtomicReference<Disposable> boundary = new AtomicReference();
        final int bufferSize;
        final Callable<? extends Publisher<B>> other;
        Subscription f2615s;
        UnicastProcessor<T> window;
        final AtomicLong windows = new AtomicLong();

        WindowBoundaryMainSubscriber(Subscriber<? super Flowable<T>> actual, Callable<? extends Publisher<B>> other, int bufferSize) {
            super(actual, new MpscLinkedQueue());
            this.other = other;
            this.bufferSize = bufferSize;
            this.windows.lazySet(1);
        }

        public void onSubscribe(Subscription s) {
            if (SubscriptionHelper.validate(this.f2615s, s)) {
                this.f2615s = s;
                Subscriber<? super Flowable<T>> a = this.actual;
                a.onSubscribe(this);
                if (!this.cancelled) {
                    try {
                        Publisher<B> p = (Publisher) ObjectHelper.requireNonNull(this.other.call(), "The first window publisher supplied is null");
                        UnicastProcessor<T> w = UnicastProcessor.create(this.bufferSize);
                        long r = requested();
                        if (r != 0) {
                            a.onNext(w);
                            if (r != Long.MAX_VALUE) {
                                produced(1);
                            }
                            this.window = w;
                            WindowBoundaryInnerSubscriber<T, B> inner = new WindowBoundaryInnerSubscriber(this);
                            if (this.boundary.compareAndSet(null, inner)) {
                                this.windows.getAndIncrement();
                                s.request(Long.MAX_VALUE);
                                p.subscribe(inner);
                                return;
                            }
                            return;
                        }
                        s.cancel();
                        a.onError(new MissingBackpressureException("Could not deliver first window due to lack of requests"));
                    } catch (Throwable e) {
                        Exceptions.throwIfFatal(e);
                        s.cancel();
                        a.onError(e);
                    }
                }
            }
        }

        public void onNext(T t) {
            if (!this.done) {
                if (fastEnter()) {
                    this.window.onNext(t);
                    if (leave(-1) == 0) {
                        return;
                    }
                }
                this.queue.offer(NotificationLite.next(t));
                if (!enter()) {
                    return;
                }
                drainLoop();
            }
        }

        public void onError(Throwable t) {
            if (this.done) {
                RxJavaPlugins.onError(t);
                return;
            }
            this.error = t;
            this.done = true;
            if (enter()) {
                drainLoop();
            }
            if (this.windows.decrementAndGet() == 0) {
                DisposableHelper.dispose(this.boundary);
            }
            this.actual.onError(t);
        }

        public void onComplete() {
            if (!this.done) {
                this.done = true;
                if (enter()) {
                    drainLoop();
                }
                if (this.windows.decrementAndGet() == 0) {
                    DisposableHelper.dispose(this.boundary);
                }
                this.actual.onComplete();
            }
        }

        public void request(long n) {
            requested(n);
        }

        public void cancel() {
            this.cancelled = true;
        }

        void drainLoop() {
            SimplePlainQueue<Object> q = this.queue;
            Subscriber<? super Flowable<T>> a = this.actual;
            int missed = 1;
            UnicastProcessor<T> w = this.window;
            while (true) {
                boolean d = this.done;
                Object o = q.poll();
                boolean empty = o == null;
                if (d && empty) {
                    break;
                } else if (empty) {
                    missed = leave(-missed);
                    if (missed == 0) {
                        return;
                    }
                } else if (o == NEXT) {
                    w.onComplete();
                    if (this.windows.decrementAndGet() == 0) {
                        DisposableHelper.dispose(this.boundary);
                        return;
                    } else if (this.cancelled) {
                        continue;
                    } else {
                        try {
                            Publisher<B> p = (Publisher) ObjectHelper.requireNonNull(this.other.call(), "The publisher supplied is null");
                            w = UnicastProcessor.create(this.bufferSize);
                            long r = requested();
                            if (r != 0) {
                                this.windows.getAndIncrement();
                                a.onNext(w);
                                if (r != Long.MAX_VALUE) {
                                    produced(1);
                                }
                                this.window = w;
                                WindowBoundaryInnerSubscriber<T, B> b = new WindowBoundaryInnerSubscriber(this);
                                if (this.boundary.compareAndSet(this.boundary.get(), b)) {
                                    p.subscribe(b);
                                }
                            } else {
                                this.cancelled = true;
                                a.onError(new MissingBackpressureException("Could not deliver new window due to lack of requests"));
                            }
                        } catch (Throwable e) {
                            Exceptions.throwIfFatal(e);
                            DisposableHelper.dispose(this.boundary);
                            a.onError(e);
                            return;
                        }
                    }
                } else {
                    w.onNext(NotificationLite.getValue(o));
                }
            }
            DisposableHelper.dispose(this.boundary);
            Throwable e2 = this.error;
            if (e2 != null) {
                w.onError(e2);
            } else {
                w.onComplete();
            }
        }

        void next() {
            this.queue.offer(NEXT);
            if (enter()) {
                drainLoop();
            }
        }
    }

    public FlowableWindowBoundarySupplier(Flowable<T> source, Callable<? extends Publisher<B>> other, int bufferSize) {
        super(source);
        this.other = other;
        this.bufferSize = bufferSize;
    }

    protected void subscribeActual(Subscriber<? super Flowable<T>> s) {
        this.source.subscribe(new WindowBoundaryMainSubscriber(new SerializedSubscriber(s), this.other, this.bufferSize));
    }
}
