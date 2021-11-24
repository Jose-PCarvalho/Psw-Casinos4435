package BlackJack.Players;

public class Base_participant {


    private String name;
  
    private int age;
    
    private String gender;

    public Base_participant(String name,int age, String gender){
        this.name=name;
        this.age=age;
        this.gender=gender;
    }

    public int getAge(){
        return age;
    }

    public String getName(){
        return name;
    }
    
    public String getGender(){
        return gender;
    }



}
