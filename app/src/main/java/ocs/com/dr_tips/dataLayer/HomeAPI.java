package ocs.com.dr_tips.dataLayer;

import java.util.HashMap;

import ocs.com.dr_tips.model.Tip;
import rx.Observable;

/**
 * Created by Randa on 3/18/2018.
 */

public interface HomeAPI {
    Observable<HashMap<String,Tip>> getTips();

}
