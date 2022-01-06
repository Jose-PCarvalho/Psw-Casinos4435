package BlackJack.Players;

public class Base_participant {


    private String name;
  
    private int age;
    
    private String gender;

    public Base_participant(String name){
        this.name=name;
       
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
