package io.reactivex.subscribers;

import io.reactivex.FlowableSubscriber;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.AppendOnlyLinkedArrayList;
import io.reactivex.internal.util.NotificationLite;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class SerializedSubscriber<T> implements FlowableSubscriber<T>, Subscription {
    static final int QUEUE_LINK_SIZE = 4;
    final Subscriber<? super T> actual;
    final boolean delayError;
    volatile boolean done;
    boolean emitting;
    AppendOnlyLinkedArrayList<Object> queue;
    Subscription subscription;

    public SerializedSubscriber(Subscriber<? super T> actual) {
        this(actual, false);
    }

    public SerializedSubscriber(Subscriber<? super T> actual, boolean delayError) {
        this.actual = actual;
        this.delayError = delayError;
    }

    public void onSubscribe(Subscription s) {
        if (SubscriptionHelper.validate(this.subscription, s)) {
            this.subscription = s;
            this.actual.onSubscribe(this);
        }
    }

    public void onNext(T t) {
        if (!this.done) {
            if (t == null) {
                this.subscription.cancel();
                onError(new NullPointerException("onNext called with null. Null values are generally not allowed in 2.x operators and sources."));
                return;
            }
            synchronized (this) {
                if (this.done) {
                } else if (this.emitting) {
                    AppendOnlyLinkedArrayList<Object> q = this.queue;
                    if (q == null) {
                        q = new AppendOnlyLinkedArrayList(4);
                        this.queue = q;
                    }
                    q.add(NotificationLite.next(t));
                } else {
                    this.emitting = true;
                    this.actual.onNext(t);
                    emitLoop();
                }
            }
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onError(java.lang.Throwable r5) {
        /*
        r4 = this;
        r3 = r4.done;
        if (r3 == 0) goto L_0x0008;
    L_0x0004:
        io.reactivex.plugins.RxJavaPlugins.onError(r5);
    L_0x0007:
        return;
    L_0x0008:
        monitor-enter(r4);
        r3 = r4.done;	 Catch:{ all -> 0x0035 }
        if (r3 == 0) goto L_0x0015;
    L_0x000d:
        r2 = 1;
    L_0x000e:
        monitor-exit(r4);	 Catch:{ all -> 0x0035 }
        if (r2 == 0) goto L_0x0044;
    L_0x0011:
        io.reactivex.plugins.RxJavaPlugins.onError(r5);
        goto L_0x0007;
    L_0x0015:
        r3 = r4.emitting;	 Catch:{ all -> 0x0035 }
        if (r3 == 0) goto L_0x003c;
    L_0x0019:
        r3 = 1;
        r4.done = r3;	 Catch:{ all -> 0x0035 }
        r1 = r4.queue;	 Catch:{ all -> 0x0035 }
        if (r1 != 0) goto L_0x0028;
    L_0x0020:
        r1 = new io.reactivex.internal.util.AppendOnlyLinkedArrayList;	 Catch:{ all -> 0x0035 }
        r3 = 4;
        r1.<init>(r3);	 Catch:{ all -> 0x0035 }
        r4.queue = r1;	 Catch:{ all -> 0x0035 }
    L_0x0028:
        r0 = io.reactivex.internal.util.NotificationLite.error(r5);	 Catch:{ all -> 0x0035 }
        r3 = r4.delayError;	 Catch:{ all -> 0x0035 }
        if (r3 == 0) goto L_0x0038;
    L_0x0030:
        r1.add(r0);	 Catch:{ all -> 0x0035 }
    L_0x0033:
        monitor-exit(r4);	 Catch:{ all -> 0x0035 }
        goto L_0x0007;
    L_0x0035:
        r3 = move-exception;
        monitor-exit(r4);	 Catch:{ all -> 0x0035 }
        throw r3;
    L_0x0038:
        r1.setFirst(r0);	 Catch:{ all -> 0x0035 }
        goto L_0x0033;
    L_0x003c:
        r3 = 1;
        r4.done = r3;	 Catch:{ all -> 0x0035 }
        r3 = 1;
        r4.emitting = r3;	 Catch:{ all -> 0x0035 }
        r2 = 0;
        goto L_0x000e;
    L_0x0044:
        r3 = r4.actual;
        r3.onError(r5);
        goto L_0x0007;
        */
        throw new UnsupportedOperationException("Method not decompiled: io.reactivex.subscribers.SerializedSubscriber.onError(java.lang.Throwable):void");
    }

    public void onComplete() {
        if (!this.done) {
            synchronized (this) {
                if (this.done) {
                } else if (this.emitting) {
                    AppendOnlyLinkedArrayList<Object> q = this.queue;
                    if (q == null) {
                        q = new AppendOnlyLinkedArrayList(4);
                        this.queue = q;
                    }
                    q.add(NotificationLite.complete());
                } else {
                    this.done = true;
                    this.emitting = true;
                    this.actual.onComplete();
                }
            }
        }
    }

    void emitLoop() {
        AppendOnlyLinkedArrayList<Object> q;
        do {
            synchronized (this) {
                q = this.queue;
                if (q == null) {
                    this.emitting = false;
                    return;
                }
                this.queue = null;
            }
        } while (!q.accept(this.actual));
    }

    public void request(long n) {
        this.subscription.request(n);
    }

    public void cancel() {
        this.subscription.cancel();
    }
}
