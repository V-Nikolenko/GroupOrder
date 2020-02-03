chrome.tabs.onUpdated.addListener((tabId, changeInfo, tab) => {
    
    if(typeof tab != "undefined" && typeof tab != "null" )
    {
        if (/misteram/.test(tab.url)) 
        {
            chrome.pageAction.show(tabId);

            // const urlParams = new URL(tab.url);
            
            // if (urlParams.searchParams.get('code')) {
            //     chrome.tabs.query({active: true, currentWindow: true}, function(tabs){
            //         chrome.tabs.sendMessage(tabs[0].id, {type: "URLcode", connectCode: urlParams.searchParams.get('code')});  
            //     });
            // }
            
        
        }
        else {
            chrome.pageAction.hide(tabId);
        }
    } else chrome.pageAction.hide(tabId);
});