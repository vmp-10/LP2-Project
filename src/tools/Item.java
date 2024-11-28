package tools;
//Property of big white nigga
public abstract class Item {
    protected String name;
    protected String type;
    protected String effectDescription;

    public Item(String name, String type, String effectDescription){
        this.name = name;
        this.type = type;
        this.effectDescription =  effectDescription;

    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public void useItem() {

    }

    public String getEffectDescription() {
        return "";
    }
}
