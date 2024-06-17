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

public class TestDistance : MonoBehaviour
{
    public GameObject prefab;
    public TextMeshProUGUI distanceText;
    public LineRenderer connectLine;
    private int isFirstPoint = 0;
    Vector3 firstPoint;
    Vector3 secondPoint;

    void SetUp() {
        connectLine.positionCount = 2;
    }

    void Update()
    {
        if (Input.touchCount > 0)
        {
            Touch touch = Input.GetTouch(0);
            if (touch.phase == TouchPhase.Began)
            {
                // Lấy điểm chạm và độ sâu tại điểm đó
                Vector2 screenPosition = touch.position;
                short[] depthMap = DepthSource.DepthArray;
                Vector2Int depthXY = DepthSource.DepthUVtoXY(screenPosition);
                float depth = DepthSource.GetDepthFromUV(screenPosition, depthMap);

                // Chuyển đổi điểm chạm trên màn hình thành vị trí trong thế giới thực
                Vector3 worldPosition = Camera.main.ScreenToWorldPoint(new Vector3(screenPosition.x, screenPosition.y, depth));

                if (isFirstPoint == 0)
                {
                    firstPoint = worldPosition;
                    distanceText.text = "Distance:" + "0";
                    isFirstPoint = 1;
                }
                else if (isFirstPoint == 1)
                {
                    secondPoint = worldPosition;
                    float distance = Vector3.Distance(firstPoint, secondPoint);
                    distanceText.text =  "Distance:" + distance.ToString();
                    connectLine.SetPosition(0, secondPoint);
                    connectLine.SetPosition(1, firstPoint);
                    isFirstPoint = 2;
                }
                else // Nếu cả hai điểm đều đã được tạo, xóa chúng và tạo điểm mới
                {
                    firstPoint = worldPosition;
                    secondPoint = new Vector3(0f, 0f, 0f);
                    distanceText.text =  "Distance:" + "0";
                    connectLine.SetPosition(0, new Vector3(0f, 0f, 0f));
                    connectLine.SetPosition(1, new Vector3(0f, 0f, 0f));
                    isFirstPoint = 1;
                }
            }
        }
    }


    


    // private void Update()
    // {
    //     short[] depthMap = DepthSource.DepthArray;
    //     // Đặt vị trí UV là giữa màn hình
    //     Vector2 depthUV = new Vector2(0.5f, 0.5f);
    //     Vector2Int depthXY = DepthSource.DepthUVtoXY(depthUV);
    //     float depth_m = DepthSource.GetDepthFromUV(depthUV, depthMap);
    //     distanceText.text = "Depth: " + depth_m.ToString();
    // }



}