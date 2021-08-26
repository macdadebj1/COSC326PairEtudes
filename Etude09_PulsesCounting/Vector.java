public class Vector{

	long x,y,z;

	public Vector(int x,y,z){
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Vector(){
		this.x = 0;
		this.y = 0;
		this.z = 0;
	}
	
	public long dot(Vector v){
		return (this.x * v.x) + (this.y * v.y) + (this.z * v.z);
	}
	
	public Vector cross(Vector v){
		
	}
	
	public String toString(){
		return String.format("<%f,%f,%f>",x,y,z);
	}
	
}