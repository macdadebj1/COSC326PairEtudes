public class Vector2{

	Double x,y;

	public Vector2(Double x, Double y){
		this.x = x;
		this.y = y;
	}

	public Vector2(){
		this.x = 0.0;
		this.y = 0.0;

	}
	
	public Double dot(Vector2 v){
		return (this.x * v.x) + (this.y * v.y);
	}
	
	/*public Vector2 cross(Vector2 v){
		
	}*/
	
	public String toString(){
		return String.format("<%f,%f>",this.x,this.y);
	}
	
}