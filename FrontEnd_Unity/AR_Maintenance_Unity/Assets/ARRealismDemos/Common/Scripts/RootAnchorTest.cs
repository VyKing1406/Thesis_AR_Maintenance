using System.Collections;
using System.Collections.Generic;
using System.Diagnostics.Tracing;
using UnityEngine;
using UnityEngine.XR.ARFoundation;
using TMPro;

public class RootAnchorTest : MonoBehaviour
{
    [SerializeField]
    ARTrackedImageManager m_TrackedImageManager;
    public GameObject root;
    public GameObject maintenancePrefab;
    private int stept = 25;


    void Start() {

    }

    void OnEnable() => m_TrackedImageManager.trackedImagesChanged += OnChanged;

    void OnDisable() => m_TrackedImageManager.trackedImagesChanged -= OnChanged;

    void OnChanged(ARTrackedImagesChangedEventArgs eventArgs)
    {
        foreach (var updatedImage in eventArgs.updated) {
            if(stept == 0) {
                GameObject object1 = Instantiate(maintenancePrefab);
                object1.transform.position = updatedImage.transform.position + new Vector3(0.15f, 0.15f, 0f);
                object1.transform.rotation = Quaternion.LookRotation(-Camera.main.transform.forward, Camera.main.transform.up);
                object1.transform.localScale = new Vector3(0.003f, 0.0015f, 0);

                GameObject object2 = Instantiate(maintenancePrefab);
                object2.transform.position = updatedImage.transform.position + new Vector3(-0.15f, -0.15f, 0f);
                object2.transform.rotation = Quaternion.LookRotation(-Camera.main.transform.forward, Camera.main.transform.up);
                object2.transform.localScale = new Vector3(0.003f, 0.0015f, 0);

                GameObject object3 = Instantiate(maintenancePrefab);
                object3.transform.position = updatedImage.transform.position + new Vector3(0.15f, -0.15f, 0f);
                object3.transform.rotation = Quaternion.LookRotation(-Camera.main.transform.forward, Camera.main.transform.up);
                object3.transform.localScale = new Vector3(0.003f, 0.0015f, 0);

                GameObject object4 = Instantiate(maintenancePrefab);
                object4.transform.position = updatedImage.transform.position + new Vector3(-0.15f, 0.15f, 0f);
                object4.transform.rotation = Quaternion.LookRotation(-Camera.main.transform.forward, Camera.main.transform.up);
                object4.transform.localScale = new Vector3(0.003f, 0.0015f, 0);
                stept--;
            }
            if(stept > 0 ) {
                stept--;
            }
        }


        foreach (var removedImage in eventArgs.removed) {
            // Handle remove image
            // ClearPositionText();
        }
    }

}
