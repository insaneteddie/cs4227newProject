package core.user;
//Making commit to show commit messaging.
public abstract class User {
    protected int id;
    protected String name;

    public abstract int getId();

    public abstract void setId(int id);

    public abstract String getName();

    public abstract void setName(String name);
}
