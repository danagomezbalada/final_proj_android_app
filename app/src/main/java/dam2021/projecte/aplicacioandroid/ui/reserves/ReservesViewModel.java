package dam2021.projecte.aplicacioandroid.ui.reserves;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ReservesViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ReservesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Aqui surten les reserves.");
    }

    public LiveData<String> getText() {
        return mText;
    }
}