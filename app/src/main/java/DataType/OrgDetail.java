package DataType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Abhay on 17-09-2016.
 */
public class OrgDetail {

    public String orgName;
    public String id;
    public String parent;
    public int depth;
    public List<OrgDetail> children;

    public OrgDetail(String name, String id, String parentId, int depth) {
        this.orgName = name;
        this.id = id;
        this.parent= parentId;
        this.depth = depth;
    }

    public void add(OrgDetail node){
        if (children == null)
            children = new ArrayList<OrgDetail>();
        children.add(node);
    }
}