package cf.huzpsb.bib;

public class ViewerConst {
    private static final String view = "<!DOCTYPE html><html><head><meta charset=\"utf-8\"><title>LatexTools::Ams</title><style>#container{text-align:center;margin-top:20px}#title{font-size:32px;margin-bottom:20px}#box{width:500px;height:300px;background-color:lightgray;margin:0 auto;display:flex;justify-content:center;align-items:center;font-size:14px;white-space:pre-wrap;padding:10px}#back-btn,#copy-btn{margin-top:20px;background-color:#4CAF50;color:#fff;padding:10px;border:none;border-radius:4px;cursor:pointer;font-size:16px}</style><script>function fetchData(){fetch('https://apis.huzpsb.eu.org/ams/fetch?id=%ID%').then(response=>response.text()).then(data=>{if(data==='queue'){setTimeout(fetchData,5000)}else{const formattedText=data.replace(/\\n/g,'<br>');document.getElementById('content').innerHTML=formattedText}}).catch(error=>{document.getElementById('content').textContent='出错了';console.log('Error:',error)})}window.onload=function(){document.getElementById('content').textContent='排队中';fetchData()};function goBack(){window.location.replace(\"https://apis.huzpsb.eu.org/grab.html\")}function copyContent(){const contentElement=document.getElementById('content');const range=document.createRange();range.selectNode(contentElement);window.getSelection().removeAllRanges();window.getSelection().addRange(range);document.execCommand('copy');window.getSelection().removeAllRanges()}</script></head><body><div id=\"container\"><h1 id=\"title\">Ams公式</h1><div id=\"box\"><p id=\"content\"></p></div><button id=\"copy-btn\"onclick=\"copyContent()\">复制</button><button id=\"back-btn\"onclick=\"goBack()\">返回</button></div></body></html>";

    public static String getView(String id) {
        return view.replace("%ID%", id);
    }
}
