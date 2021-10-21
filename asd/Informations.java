package Models;

public class Informations {
    private String type, name;
    private int age;

    public Informations() {
    }

    public Informations(String type, String name, int age) {
        this.type = type;
        this.name = name;
        this.age = age;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }


    @Override
    public String toString() {
        return "Informations{" +
                "type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
