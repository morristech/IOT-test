package com.biz.health.cooey_app.generators;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(bv = {1, 0, 2}, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0006¨\u0006\b"}, d2 = {"Lcom/biz/health/cooey_app/generators/VitalInputGenerator;", "", "()V", "generateVitalInput", "", "vitalType", "", "userId", "app_release"}, k = 1, mv = {1, 1, 7})
/* compiled from: VitalInputGenerator.kt */
public final class VitalInputGenerator {
    public final void generateVitalInput(@NotNull String vitalType, @NotNull String userId) {
        Intrinsics.checkParameterIsNotNull(vitalType, "vitalType");
        Intrinsics.checkParameterIsNotNull(userId, "userId");
    }
}