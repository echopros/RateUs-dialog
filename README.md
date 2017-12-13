# rate Us Dialog

Customiable RateDialog for any android app.
Example:

```
 RateDialogHelper rateDialogHelper = new RateDialogHelper.Builder()
                .setRatingColorActive(App.getColorTheme().mainColor)
                .setRatingColorInactive(App.getColorTheme().secondaryColor)
                .setTitleAppNameColor(App.getColorTheme().mainColor)
                .setCancelColor(App.getColorTheme().secondaryColor)
                .setRateColor(App.getColorTheme().mainColor)
                .setFeedbackEmail("developeremail@gmail.com")
                .build();

        rateDialogHelper.showRateDialog(this);
```

### Integration

Add to your top-level gradle file:

```
allprojects {
    repositories {
        jcenter()

        maven {
            url "https://dl.bintray.com/spartacus777/rateusdialoghelper"
        }
        ...
    }
}
```

Add to app.gradle:
```
compile 'rateusdialoghelper:rateusdialoghelper:1.0.2@aar'
```

### Contract

Call from Application class:

```
        RateDialogHelper.onNewSession(context);
```

Call from activity you want the dialog to appear:

```
 RateDialogHelper rateDialogHelper = new RateDialogHelper.Builder()
                .setShowEveryTime(true)
                ...
                .build();

        rateDialogHelper.showRateDialog(this);
```

## Configurations

RateDialog is UI and functional-configurable.
We use builder pattern to set proper parameters, but, anyway Rate Dialog will work even with default parameters.
So we can specify:
```
setShowEveryTime(boolean b) - show dialog every time, usefull for testing
```

## Authors

* **Anton Kizema** *

## License

This project is licensed under the Apache 2.0 License - see the [LICENSE.md](LICENSE.md) file for details
You can use this lib as you want. Staring this project would be very encouraging!

