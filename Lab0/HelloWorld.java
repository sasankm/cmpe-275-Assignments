package sasank;

public class HelloWorld implements Greeter {

	
private String name;

	public String getName() {

		return name;

	}


	@Override
	public void setName(String name) {
		// TODO Auto-generated method stub
		this.name = name;
		
	}

	@Override
	public String getGreeting() {
		// TODO Auto-generated method stub
		System.out.println("Hello World from " + getName());
		return name;
	}
}
