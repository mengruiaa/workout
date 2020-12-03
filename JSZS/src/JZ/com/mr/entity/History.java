package JZ.com.mr.entity;

public class History {
    private String user_name;
    private String search;

    public History(String user_name, String search) {
        this.user_name = user_name;
        this.search = search;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
}
