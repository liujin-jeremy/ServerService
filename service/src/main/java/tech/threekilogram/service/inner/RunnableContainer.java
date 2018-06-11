package tech.threekilogram.service.inner;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author wuxio 2018-06-11:19:44
 */
public class RunnableContainer implements Parcelable {



    @Override
    public int describeContents() {

        return 0;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }


    public RunnableContainer() {

    }


    protected RunnableContainer(Parcel in) {

    }


    public static final Parcelable.Creator< RunnableContainer > CREATOR = new Parcelable.Creator<
            RunnableContainer >() {
        @Override
        public RunnableContainer createFromParcel(Parcel source) {

            return new RunnableContainer(source);
        }


        @Override
        public RunnableContainer[] newArray(int size) {

            return new RunnableContainer[size];
        }
    };
}
