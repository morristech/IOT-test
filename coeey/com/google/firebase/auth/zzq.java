package com.google.firebase.auth;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.internal.zzbek;

public final class zzq implements Creator<GoogleAuthCredential> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        String str = null;
        int zzd = zzbek.zzd(parcel);
        String str2 = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 1:
                    str2 = zzbek.zzq(parcel, readInt);
                    break;
                case 2:
                    str = zzbek.zzq(parcel, readInt);
                    break;
                default:
                    zzbek.zzb(parcel, readInt);
                    break;
            }
        }
        zzbek.zzaf(parcel, zzd);
        return new GoogleAuthCredential(str2, str);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new GoogleAuthCredential[i];
    }
}