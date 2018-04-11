package ocs.com.dr_tips.viewModel;

import java.util.HashMap;

import ocs.com.dr_tips.model.Tip;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by Randa on 3/18/2018.
 */

public interface HomeViewModel {

    Observable<HashMap<String,Tip>> getTips();


}
