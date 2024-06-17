using UnityEngine;
using UnityEngine.EventSystems;
using UnityEngine.SceneManagement;
using System.Collections.Generic;
using UnityEngine;
using System.IO;
using System.Net;
using System.Net.Http;
using UnityEngine.Networking;
using System.Text;
using UnityEngine.SceneManagement;
using TMPro;
using Newtonsoft.Json;
using System.Threading.Tasks;
using UnityEditor;
using System;

public class TestCreateObject : MonoBehaviour
{
    public GameObject orientedReticle;
    public GameObject cupe;

    private void Start()
    {
        
    }

    


    private void Update()
    {
        
    }

    public void DropButtonOnclick() {
        GameObject gameObject = Instantiate(cupe, orientedReticle.transform.position, Quaternion.identity);
        gameObject.transform.localScale = new Vector3(0.005f, 0.005f, 0f);
    }
}