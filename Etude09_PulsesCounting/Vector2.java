public class Vector2{

	Float x,y;

	public Vector2(Float x, Float y){
		this.x = x;
		this.y = y;
	}

	public Vector2(){
		this.x = 0.0f;
		this.y = 0.0f;

	}
	
	public Float dot(Vector2 v){
		return (this.x * v.x) + (this.y * v.y);
	}
	
	/*public Vector2 cross(Vector2 v){
		
	}*/
	
	public String toString(){
		return String.format("<%f,%f>",this.x,this.y);
	}
	
}