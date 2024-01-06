using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using Unity.MLAgents;
using Unity.MLAgents.Sensors;
using Unity.MLAgents.Actuators;
using TMPro;


public class PlayerAgent : Agent {
    public Transform[] Targets;
    public int playerNum;
    public TextMeshProUGUI countText;
    public GameObject winTextObject;
    
    private Rigidbody rBody;
    private int count; //tracks amount of coins collected

    void SetCountText() {
        countText.text = "P" + playerNum + " Count: " + count.ToString();

        if (count >= 12) {
            winTextObject.SetActive(true);
        }
    }

    private bool inWall(Transform coin) {
        return false;
    }

    private void RandomizeCoins() {
        foreach (Transform coin in Targets) {
            coin.gameObject.SetActive(true);
            do {
                coin.localPosition = new Vector3(
                    Random.Range(-14, 14),
                    0.5f,
                    Random.Range(-14, 14)
                );
            } while (inWall(coin));
        }
        count = 0;
        SetCountText();
    }

    void Start () {
        rBody = GetComponent<Rigidbody>();
        count = 0;
        SetCountText();
        winTextObject.SetActive(false);
    }

    public override void OnEpisodeBegin() {
        count = 0;
        SetCountText();
        RandomizeCoins();
        winTextObject.SetActive(false);
    }

    public float forceMultiplier = 10;
    public override void OnActionReceived(ActionBuffers actionBuffers) {
        var direction = Vector3.zero;
        var rotation = Vector3.zero;

        var action = actionBuffers.DiscreteActions[0];
        switch (action) {
            case 1: direction = transform.forward * 1f;   break;
            case 2: direction = transform.forward * -1f;  break;
            case 3: rotation =  transform.up * 1f;        break;
            case 4: rotation =  transform.up * -1f;       break;
            case 5: direction = transform.right * -0.75f; break;
            case 6: direction = transform.right * 0.75f;  break;
        }

        transform.Rotate(rotation, Time.fixedDeltaTime * 50f);
        rBody.AddForce(direction * 1, ForceMode.VelocityChange);
    }

    private bool AllCoinsCollected() {
        foreach (Transform coin in Targets) {
            if (coin.gameObject.activeSelf) return false;
        }
        return true;
    }

    private void OnTriggerEnter(Collider other) {
        if (other.gameObject.CompareTag("Coin")) {
            other.gameObject.SetActive(false);
            SetReward(5.0f);
            count++;
            SetCountText();
        }

        if (AllCoinsCollected()) {
            EndEpisode();
            count = 0;
            RandomizeCoins();
            SetCountText();
        }
    }

    public override void Heuristic(in ActionBuffers actionsOut) {
        var discreteActionsOut = actionsOut.DiscreteActions;
        discreteActionsOut[0] = 0;

        if (Input.GetKey(KeyCode.D)) {
            discreteActionsOut[0] = 3;
        } else if(Input.GetKey(KeyCode.W)) {
            discreteActionsOut[0] = 1;
        } else if (Input.GetKey(KeyCode.A)) {
            discreteActionsOut[0] = 4;
        } else if (Input.GetKey(KeyCode.S)) {
            discreteActionsOut[0] = 2;
        }
    }
}