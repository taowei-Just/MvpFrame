# MvpFrame
## Retrofit2+Rxjava+Okhttp+Butterknife+EventBus+Mvp  来不及了快导包
 

    // butterknife
    implementation "com.jakewharton:butterknife:$deps.butterknife"
    annotationProcessor "com.jakewharton:butterknife-compiler:$deps.butterknife_compiler"
    // eventBus
    implementation "org.greenrobot:eventbus:$deps.eventbus"

    // Retrofit库
    implementation "com.squareup.retrofit2:retrofit:$deps.retrofit"
    // gson库
    implementation "com.squareup.retrofit2:converter-gson:$deps.converter_gson"
    // Retrofit中的rxjava adapter
    implementation "com.squareup.retrofit2:adapter-rxjava2:$deps.adapter_rxjava2"
    //rxjava and rxandroid
    implementation "io.reactivex.rxjava2:rxjava:$deps.rxjava"
    implementation "io.reactivex.rxjava2:rxandroid:$deps.rxandroid"
    // okhttp
    implementation "com.squareup.okhttp3:okhttp:$deps.okhttp"
    implementation "com.squareup.okhttp3:logging-interceptor:$deps.okhttp3_logging_interceptor"
    implementation "com.google.code.gson:gson:$deps.gson"

 

    // androidx 自动解绑
    implementation "com.uber.autodispose:autodispose:$androidx.autodispose"
    implementation "com.uber.autodispose:autodispose-android:$androidx.autodispose_android"
    implementation "com.uber.autodispose:autodispose-android-archcomponents:$androidx.autodispose_android"
    implementation "com.uber.autodispose:autodispose-android-archcomponents-test:$androidx.autodispose_android_archcomponents_test"


    implementation "me.yokeyword:fragmentationx:$androidx.fragmentationx"
 


### version.gradle


ext.isUserApplication = false

def deps = [
        rxjava                            : "2.1.8",
        rxandroid                         : "2.1.1",
        adapter_rxjava2                   : "2.5.0",
        retrofit                          : "2.5.0",
        converter_gson                    : "2.5.0",
        gson                              : "2.8.5",
        okhttp                            : "3.12.1",
        okhttp3_logging_interceptor       : "3.12.1",
        autodispose                       : "0.8.0",
        autodispose_android_archcomponents: "0.8.0",
        butterknife                       : "8.4.0",
        butterknife_compiler              : "8.4.0",
        eventbus                          : "3.0.0",
        easypermissions                   : "1.3.0",
        leakcanary_android                : "1.5",
        leakcanary_android_no_op          : "1.5",
        rxpermissions                     : "0.9.4@aar",

]
def android = [
        butterknife         : "8.4.0",
        butterknife_compiler: "8.4.0",
        fragmentation       : "1.3.6"

]

def androidx = [
        autodispose                            : "1.1.0",
        autodispose_android                    : "1.1.0",
        autodispose_android_archcomponents_test: "1.1.0",
        autodispose_android_archcomponents     : "1.1.0",
        fragmentationx                         : "1.0.2"
]

ext.deps = deps
ext.android = android
ext.androidx = androidx




