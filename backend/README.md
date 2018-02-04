*(Disclaimer: Under construction)*

**Show Parkings**
----
  Returns json data about all the *check-in* and *check-out* data.

* **URL**

  /parkings/

* **Method:**

  `GET`
  
*  **URL Params**

   None

* **Data Params**

  None

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** `{ id : 1, 
                    vehicle : "4567-VDF",
                    parkingArea : 314,
                    startTime : "2018-02-04 00:49:18",
                    endTime :  "2018-02-04 00:54:20"}`
 
* **Error Response:**

  * **Code:** 404 NOT FOUND <br />
    **Content:** `{ error : "User doesn't exist" }`

  OR

  * **Code:** 401 UNAUTHORIZED <br />
    **Content:** `{ error : "You are unauthorized to make this request." }`

* **Sample Call:**

  ```javascript
    $.ajax({
      url: "/parkings/",
      dataType: "json",
      type : "GET",
      success : function(r) {
        console.log(r);
      }
    });
  ```
