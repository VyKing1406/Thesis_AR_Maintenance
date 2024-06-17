using System.Collections;
using System.Collections.Generic;
using System.Diagnostics.Tracing;
using UnityEngine;
using UnityEngine.XR.ARFoundation;
using TMPro;

public class DeviceSensor : MonoBehaviour
{
    [SerializeField]
    ARTrackedImageManager m_TrackedImageManager;
    
    public GameObject prefab;
    
    private GameObject device1;
    private TextMeshProUGUI deviceText1;
    
    private GameObject device2;
    private TextMeshProUGUI deviceText2;

    private GameObject device3;
    private TextMeshProUGUI deviceText3;

    private GameObject device4;
    private TextMeshProUGUI deviceText4;

    private GameObject device5;
    private TextMeshProUGUI deviceText5;

    private GameObject device6;
    private TextMeshProUGUI deviceText6;

    private GameObject device7;
    private TextMeshProUGUI deviceText7;

    private int stept = 1;


    void Start() {

    }

    void OnEnable() => m_TrackedImageManager.trackedImagesChanged += OnChanged;

    void OnDisable() => m_TrackedImageManager.trackedImagesChanged -= OnChanged;

    void OnChanged(ARTrackedImagesChangedEventArgs eventArgs)
    {
        foreach (var newImage in eventArgs.added) {
            // Handle added event
            if(stept == 1) {
                device1 = Instantiate(prefab, newImage.transform.position, Quaternion.identity);
                device1.transform.localScale = new Vector3(0.0005f, 0.0005f, 0f);
                device1.transform.rotation = Quaternion.LookRotation(-Camera.main.transform.forward, Camera.main.transform.up);
                deviceText1 = device1.GetComponentInChildren<TextMeshProUGUI>();
                deviceText1.text = "Device 1";
                
                device2 = Instantiate(prefab, newImage.transform.position + new Vector3(-0.01f, -0.01f, 0f), Quaternion.identity);
                device2.transform.localScale = new Vector3(0.0005f, 0.0005f, 0f);
                device2.transform.rotation = Quaternion.LookRotation(-Camera.main.transform.forward, Camera.main.transform.up);
                deviceText2 = device1.GetComponentInChildren<TextMeshProUGUI>();
                deviceText2.text = "Device 2";

                stept--;
                continue;
            }
            device1.transform.position = newImage.transform.position;
            device2.transform.position = newImage.transform.position + new Vector3(-0.01f, -0.02f, 0f);
        }


        foreach (var updatedImage in eventArgs.updated) {
            if(stept == 1) {
                device1 = Instantiate(prefab, updatedImage.transform.position, Quaternion.identity);
                device1.transform.localScale = new Vector3(0.0005f, 0.0005f, 0f);
                device1.transform.rotation = Quaternion.LookRotation(-Camera.main.transform.forward, Camera.main.transform.up);
                deviceText1 = device1.GetComponentInChildren<TextMeshProUGUI>();
                deviceText1.text = "Device 1";
                
                device2 = Instantiate(prefab, updatedImage.transform.position + new Vector3(-0.01f, -0.01f, -0.02f), Quaternion.identity);
                device2.transform.localScale = new Vector3(0.0005f, 0.0005f, 0f);
                device2.transform.rotation = Quaternion.LookRotation(-Camera.main.transform.forward, Camera.main.transform.up);
                deviceText2 = device1.GetComponentInChildren<TextMeshProUGUI>();
                deviceText2.text = "Device 2";

                stept--;
                continue;
            }
            device1.transform.position = updatedImage.transform.position;
            device2.transform.position = updatedImage.transform.position + new Vector3(-0.01f, -0.01f, 0f);
        }


        foreach (var removedImage in eventArgs.removed) {
            // Handle remove image
            // ClearPositionText();
        }
    }

}
