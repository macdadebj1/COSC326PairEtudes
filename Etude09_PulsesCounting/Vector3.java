public class Vector3{

	Float x,y,z;

	public Vector3(Float x, Float y, Float z){
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Vector3(){
		this.x = 0.0f;
		this.y = 0.0f;
		this.z = 0.0f;
	}
	
	public Float dot(Vector3 v){
		return (this.x * v.x) + (this.y * v.y) + (this.z * v.z);
	}
	
	/*public Vector3 cross(Vector3 v){
		
	}*/
	
	public String toString(){
		return String.format("<%f,%f,%f>",this.x,this.y,this.z);
	}
	
}