function parseMd(md){
  
    //ul
    md = md.replace(/^\s*\n\*/gm, '<ul>\n*');
    md = md.replace(/^(\*.+)\s*\n([^\*])/gm, '$1\n</ul>\n\n$2');
    md = md.replace(/^\*(.+)/gm, '<li>$1</li>');
    
    //ol
    md = md.replace(/^\s*\n\d\./gm, '<ol>\n1.');
    md = md.replace(/^(\d\..+)\s*\n([^\d\.])/gm, '$1\n</ol>\n\n$2');
    md = md.replace(/^\d\.(.+)/gm, '<li>$1</li>');
    
    //blockquote
    md = md.replace(/^\>(.+)/gm, '<blockquote>$1</blockquote>');
    
    //h
    md = md.replace(/[\#]{6}(.+)/g, '<h6>$1</h6>');
    md = md.replace(/[\#]{5}(.+)/g, '<h5>$1</h5>');
    md = md.replace(/[\#]{4}(.+)/g, '<h4>$1</h4>');
    md = md.replace(/[\#]{3}(.+)/g, '<h3>$1</h3>');
    md = md.replace(/[\#]{2}(.+)/g, '<h2>$1</h2>');
    md = md.replace(/[\#]{1}(.+)/g, '<h1>$1</h1>');
    
    //alt h
    md = md.replace(/^(.+)\n\=+/gm, '<h1>$1</h1>');
    md = md.replace(/^(.+)\n\-+/gm, '<h2>$1</h2>');
    
    //images
    md = md.replace(/\!\[([^\]]+)\]\(([^\)]+)\)/g, '<img src="$2" alt="$1" />');
    
    //links
    md = md.replace(/[\[]{1}([^\]]+)[\]]{1}[\(]{1}([^\)\"]+)(\"(.+)\")?[\)]{1}/g, '<a href="$2" title="$4">$1</a>');
    
    //font styles
    md = md.replace(/[\*\_]{2}([^\*\_]+)[\*\_]{2}/g, '<b>$1</b>');
    md = md.replace(/[\*\_]{1}([^\*\_]+)[\*\_]{1}/g, '<i>$1</i>');
    md = md.replace(/[\~]{2}([^\~]+)[\~]{2}/g, '<del>$1</del>');
    
    //pre
    md = md.replace(/^\s*\n\`\`\`(([^\s]+))?/gm, '<pre class="$2">');
    md = md.replace(/^\`\`\`\s*\n/gm, '</pre>\n\n');
    
    //code
    md = md.replace(/[\`]{1}([^\`]+)[\`]{1}/g, '<code>$1</code>');
    
    //p
    md = md.replace(/^\s*(\n)?(.+)/gm, function(m){
      return  /\<(\/)?(h\d|ul|ol|li|blockquote|pre|img)/.test(m) ? m : '<p>'+m+'</p>';
    });
    
    //strip p from pre
    md = md.replace(/(\<pre.+\>)\s*\n\<p\>(.+)\<\/p\>/gm, '$1$2');


    md = makeTable(md);
    return md;
    
  }

  // h
  function makeH1_H6(mdText){
    return mdText.replace(/[\#]{6}(.+)/g, '<h6>$1</h6>')
                .replace(/[\#]{5}(.+)/g, '<h5>$1</h5>')
                .replace(/[\#]{4}(.+)/g, '<h4>$1</h4>')
                .replace(/[\#]{3}(.+)/g, '<h3>$1</h3>')
                .replace(/[\#]{2}(.+)/g, '<h2>$1</h2>')
                .replace(/[\#]{1}(.+)/g, '<h1>$1</h1>');
  }
     //pre & code
  function makePreCode(mdText){
 
    return mdText.replace(/^\s*\n\`\`\`(([^\s]+))?/gm, '<pre class="$2">')
                  .replace(/^\`\`\`\s*\n/gm, '</pre>\n\n')
                  .replace(/[\`]{1}([^\`]+)[\`]{1}/g, '<code>$1</code>');
  }
  // ol, ul, li
  function makeOlUlLi(mdText){
    return mdText.replace(/^\s*\n\*/gm, '<ul>\n*')
                 .replace(/^(\*.+)\s*\n([^\*])/gm, '$1\n</ul>\n\n$2')
                 .replace(/^\*(.+)/gm, '<li>$1</li>')
                 .replace(/^\s*\n\d\./gm, '<ol>\n1.')
                 .replace(/^(\d\..+)\s*\n([^\d\.])/gm, '$1\n</ol>\n\n$2')
                 .replace(/^\d\.(.+)/gm, '<li>$1</li>');
  }
  
  // table
  function makeTable(mdText){
    if(!/[|]+?.*[|]/g.test(mdText)){
        return mdText;
    }
    var m = String(mdText).match(/[|]+?.*[|]/g);
    var array = [];
      ps = "<tr>"
      for(var i=0; i < m.length; i++){
          var b = m[i].split("|");
          array.push(b);
      }
    
    var htmlStr = "<table><thead>";
    for(var i=0; i < array.length; i++){
        if(i!=1){
            htmlStr += (i!=2) ? "<tr>" : "<tbody><tr>";
            for(var j=1; j < array[i].length-1 ; j++){
                htmlStr += (i!=0)? "<td>" + array[i][j] + "</td>" :  "<th>" + array[i][j] + "</th>" ;
            }
            htmlStr += (i!=0) ? "</tr>" : "</tr></thead>";
        }
    }
     htmlStr += "</tbody></table>"
    //return htmlStr;
     return mdText.replace(/[|]+?.*[|]/, htmlStr)
                  .replace(/[|]+?.*[|]/g,"");
  }

  var rawMode = true;
      mdEl = document.getElementById('markdown'),
      outputEl = document.getElementById('output-html'),
      parse = function(){
        outputEl[rawMode ? "innerText" : "innerHTML"] = parseMd(mdEl.innerText);
        
      };

  parse();
  mdEl.addEventListener('keyup', parse, false);
  
  //Raw mode trigger btn
  (function(){
  
    var trigger = document.getElementById('raw-switch'),
        status = trigger.getElementsByTagName('span')[0],
        updateStatus = function(){
          status.innerText = rawMode ? 'On' : 'Off';
        };
  
    updateStatus();
    trigger.addEventListener('click', function(e){
      e.preventDefault();
      rawMode = rawMode ? false : true;
      updateStatus();
      parse();
    }, false);
    
  }());