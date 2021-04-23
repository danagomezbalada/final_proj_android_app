package dam2021.projecte.aplicacioandroid.ui.cercar;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CercarViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public CercarViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Aqui es pot cercar");
    }

    public LiveData<String> getText() {
        return mText;
    }
}