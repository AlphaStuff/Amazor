package com.github.alphastuff.amazor;

import com.panjohnny.LogFormat;
import com.panjohnny.LogProperty;
import com.panjohnny.Logger;

/**
 * @author PanJohnny
 * @author Domo
 */
public class Amazor {

    public Amazor() {

    }
    public static void main(String[] args) {
        Logger.setFormat(new LogFormat("[%] (%): ", '%', LogProperty.THREAD_NAME, LogProperty.DATE_TIME));

    }
}
