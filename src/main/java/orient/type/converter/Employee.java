package orient.type.converter;
public class Employee {

    enum Type{Manager, Staffmember}

    public Type type;
    private String name;
    private Integer age;
    private Double salary;
    private String gender;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Integer getAge() {
        return age;
    }
    public void setAge(Integer age) {
        this.age = age;
    }
    public Double getSalary() {
        return salary;
    }
    public void setSalary(Double salary) {
        this.salary = salary;
    }
    public String getGender() {
        return gender;
    }
    public void setGender(String String) {
        this.gender = gender;
    }
    @Override
    public String toString() {
        return "Employee [name=" + name + ", age=" + age + ", salary=" + salary
                + ", gender=" + gender + "]";
    }
}
