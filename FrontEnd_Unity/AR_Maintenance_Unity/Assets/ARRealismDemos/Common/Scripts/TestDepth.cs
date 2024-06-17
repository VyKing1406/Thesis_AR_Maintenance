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

public class TestDepth : MonoBehaviour
{
    public GameObject orientedReticle;
    public TextMeshProUGUI depthText;

    private async void Start()
    {
        
    }

    


    private void Update()
    {
        short[] depthMap = DepthSource.DepthArray;
        // Đặt vị trí UV là giữa màn hình
        Vector2 depthUV = new Vector2(0.5f, 0.5f);
        Vector2Int depthXY = DepthSource.DepthUVtoXY(depthUV);
        float depth_m = DepthSource.GetDepthFromUV(depthUV, depthMap);
        depthText.text = "Depth: " + depth_m.ToString();
    }
}