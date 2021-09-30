public class Vector2{

	long x,y;

	public Vector2(int x, int y){
		this.x = x;
		this.y = y;
	}

	public Vector2(){
		this.x = 0;
		this.y = 0;

	}
	
	public long dot(Vector2 v){
		return (this.x * v.x) + (this.y * v.y);
	}
	
	/*public Vector2 cross(Vector2 v){
		
	}*/
	
	public String toString(){
		return String.format("<%f,%f,%f>",x,y,z);
	}
	
}