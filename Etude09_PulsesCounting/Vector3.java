public class Vector3{

	Double x,y,z;

	public Vector3(Double x, Double y, Double z){
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Vector3(){
		this.x = 0.0;
		this.y = 0.0;
		this.z = 0.0;
	}
	
	public Double dot(Vector3 v){
		return (this.x * v.x) + (this.y * v.y) + (this.z * v.z);
	}
	
	/*public Vector3 cross(Vector3 v){
		
	}*/
	
	public String toString(){
		return String.format("<%f,%f,%f>",this.x,this.y,this.z);
	}
	
}