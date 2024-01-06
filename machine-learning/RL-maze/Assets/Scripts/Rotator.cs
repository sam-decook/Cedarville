using UnityEngine;
using System.Collections;

public class Rotator : MonoBehaviour {

	// Before rendering each frame..
	void Update () 
	{
		transform.Rotate(new Vector3(30, 30, 0) * Time.deltaTime);
	}
}	