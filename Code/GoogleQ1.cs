using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.Networking;
public class GoogleQ1 : MonoBehaviour
{
    [SerializeField] private TMPro.TextMeshProUGUI txtData1;
    [SerializeField] private UnityEngine.UI.Button btnSubmit;
    [SerializeField] private TMPro.TextMeshProUGUI txtData2;
    [SerializeField] private UnityEngine.UI.Button btnSubmit2;    
    [SerializeField] private TMPro.TextMeshProUGUI txtData3;
    [SerializeField] private UnityEngine.UI.Button btnSubmit3;    
    [SerializeField] private TMPro.TextMeshProUGUI txtData4;
    [SerializeField] private UnityEngine.UI.Button btnSubmit4;    
    [SerializeField] private TMPro.TextMeshProUGUI txtData5;
    [SerializeField] private UnityEngine.UI.Button btnSubmit5;

    private const string kGFormBaseURL = "https://docs.google.com/forms/d/1RphaEdpzDRYCQt0BYtfaqZ4VM73BOlvFg2iXxiVwMks/";
    private const string kGFormEntryID = "entry.1410775385"; 
    private const string kGFormEntryID2 = "entry.1524752108";   
    private const string kGFormEntryID3 = "entry.507165256";    
    private const string kGFormEntryID4 = "entry.331338693";   
    private const string kGFormEntryID5 = "entry.37675932";

    void Start()
    {
        UnityEngine.Assertions.Assert.IsNotNull(txtData1);
        UnityEngine.Assertions.Assert.IsNotNull(btnSubmit);
        btnSubmit.onClick.AddListener(delegate
        {
            StartCoroutine(SendGFormData(txtData1.text));
            btnSubmit.gameObject.SetActive(false);
        });
        
        UnityEngine.Assertions.Assert.IsNotNull(txtData2);
        UnityEngine.Assertions.Assert.IsNotNull(btnSubmit2);
        btnSubmit2.onClick.AddListener(delegate
        {
            StartCoroutine(SendGFormData2(txtData2.text));
            btnSubmit2.gameObject.SetActive(false);
        }); 
        
        UnityEngine.Assertions.Assert.IsNotNull(txtData3);
        UnityEngine.Assertions.Assert.IsNotNull(btnSubmit3);
        btnSubmit3.onClick.AddListener(delegate
        {
            StartCoroutine(SendGFormData3(txtData3.text));
            btnSubmit3.gameObject.SetActive(false);
        }); 
        
        UnityEngine.Assertions.Assert.IsNotNull(txtData4);
        UnityEngine.Assertions.Assert.IsNotNull(btnSubmit4);
        btnSubmit4.onClick.AddListener(delegate
        {
            StartCoroutine(SendGFormData4(txtData4.text));
            btnSubmit4.gameObject.SetActive(false);
        }); 
        
        UnityEngine.Assertions.Assert.IsNotNull(txtData5);
        UnityEngine.Assertions.Assert.IsNotNull(btnSubmit5);
        btnSubmit5.onClick.AddListener(delegate
        {
            StartCoroutine(SendGFormData5(txtData5.text));
            btnSubmit5.gameObject.SetActive(false);
        });
    }


    private static IEnumerator SendGFormData<T>(T dataContainer)
    {
        bool isString = dataContainer is string;
        string jsonData = isString ? dataContainer.ToString() : JsonUtility.ToJson(dataContainer);

        WWWForm form = new WWWForm();
        form.AddField(kGFormEntryID, jsonData);
        string urlGFormResponse = kGFormBaseURL + "formResponse";
        using (UnityWebRequest www = UnityWebRequest.Post(urlGFormResponse, form))
        {
            yield return www.SendWebRequest();
        }
    }

    private static IEnumerator SendGFormData2<T>(T dataContainer)
    {
        bool isString = dataContainer is string;
        string jsonData = isString ? dataContainer.ToString() : JsonUtility.ToJson(dataContainer);

        WWWForm form = new WWWForm();
        form.AddField(kGFormEntryID2, jsonData);
        string urlGFormResponse = kGFormBaseURL + "formResponse";
        using (UnityWebRequest www = UnityWebRequest.Post(urlGFormResponse, form))
        {
            yield return www.SendWebRequest();
        }
    }

    private static IEnumerator SendGFormData3<T>(T dataContainer)
    {
        bool isString = dataContainer is string;
        string jsonData = isString ? dataContainer.ToString() : JsonUtility.ToJson(dataContainer);

        WWWForm form = new WWWForm();
        form.AddField(kGFormEntryID3, jsonData);
        string urlGFormResponse = kGFormBaseURL + "formResponse";
        using (UnityWebRequest www = UnityWebRequest.Post(urlGFormResponse, form))
        {
            yield return www.SendWebRequest();
        }
    }
    
    private static IEnumerator SendGFormData4<T>(T dataContainer)
    {
        bool isString = dataContainer is string;
        string jsonData = isString ? dataContainer.ToString() : JsonUtility.ToJson(dataContainer);

        WWWForm form = new WWWForm();
        form.AddField(kGFormEntryID4, jsonData);
        string urlGFormResponse = kGFormBaseURL + "formResponse";
        using (UnityWebRequest www = UnityWebRequest.Post(urlGFormResponse, form))
        {
            yield return www.SendWebRequest();
        }
    } 
    
    private static IEnumerator SendGFormData5<T>(T dataContainer)
    {
        bool isString = dataContainer is string;
        string jsonData = isString ? dataContainer.ToString() : JsonUtility.ToJson(dataContainer);

        WWWForm form = new WWWForm();
        form.AddField(kGFormEntryID5, jsonData);
        string urlGFormResponse = kGFormBaseURL + "formResponse";
        using (UnityWebRequest www = UnityWebRequest.Post(urlGFormResponse, form))
        {
            yield return www.SendWebRequest();
        }
    }
}
