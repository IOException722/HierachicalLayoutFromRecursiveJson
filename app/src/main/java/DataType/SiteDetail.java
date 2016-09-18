package DataType;

/**
 * Created by Abhay on 17-09-2016.
 */
public class SiteDetail {
    public String id;
    public String siteName;
    public int organizationId;

    public SiteDetail(String id,String siteName, int organizationid)
    {
        this.id = id;
        this.siteName = siteName;
        this.organizationId = organizationid;
    }
}
