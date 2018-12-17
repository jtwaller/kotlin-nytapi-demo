# Kotlin NYT Api Demo

This application fetches articles from the New York Times api and displays results in a recycler view.  On click, articles will be loaded within a WebView with a share aciton provider included in the action bar.

If you intend to build and run this application, please create "secrets.xml" within your "res/values" folder and include your api key, e.g., 
```<?xml version="1.0" encoding="utf-8"?>
<resources>
    <string name="nyt_api_key">KEY HERE</string>
</resources>```
