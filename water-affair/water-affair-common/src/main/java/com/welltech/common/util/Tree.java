/**
 * 
 */
package com.welltech.common.util;

import java.util.ArrayList;
import java.util.List;

import com.welltech.entity.WtMenu;

/**
 * Created by Zhujia at 2017年8月30日 上午10:33:32
 */
public class Tree {  
    private StringBuffer html = new StringBuffer();  
    private List<WtMenu> nodes;  
      
    public Tree(List<WtMenu> nodes){  
        this.nodes = nodes;  
    }  
      
    public String buildTree(){  
        for (WtMenu node : nodes) {  
            Integer id = node.getId();  
            if (node.getpId() == 0) {  
                build(node);  
            }  
        }  
        return html.toString();  
    }  
      
    private void build(WtMenu node){  
        List<WtMenu> children = getChildren(node);
        if(node.getpId()==0){
        	html.append("\r\n<li>");  
        	html.append("\r\n<a href='"+node.getUrl()+"'><i class='fa "+node.getIcon()+"'></i> <span class='nav-label'>"+node.getName()+"</span> <span class='fa arrow'></span></a>");
        }
        if (!children.isEmpty()) {  
            html.append("\r\n<ul class='nav nav-second-level collapse'>");
            for (WtMenu child : children) {  
                Integer id = child.getId();  
                
                html.append("\r\n<li><a href='"+child.getUrl()+"'>"+child.getName()+"</a></li>");  
                build(child);  
            }  
            html.append("\r\n</ul>");  
        }
        if(node.getpId()==0){
        	html.append("\r\n</li>");  
        }
    }  
      
    private List<WtMenu> getChildren(WtMenu node){  
        List<WtMenu> children = new ArrayList<WtMenu>();  
        Integer id = node.getId();  
        for (WtMenu child : nodes) {  
            if (id.equals(child.getpId())) {  
                children.add(child);  
            }  
        }  
        return children;  
    }  
}  
