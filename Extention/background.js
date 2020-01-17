function checkForValidUrl(tabId, changeInfo, tab) {
    if(typeof tab != "undefined" && typeof tab != "null" )
    {
        if (/misteram/.test(tab.url)) 
        {
            chrome.pageAction.show(tabId);
        }
        else chrome.pageAction.hide(tabId);
    } else chrome.pageAction.hide(tabId);
};

// Listen for any changes to the URL of any tab.
chrome.tabs.onUpdated.addListener(checkForValidUrl);