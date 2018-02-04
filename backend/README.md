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
    **Content:** `[{ id : 1, 
                    vehicle : "4567-VDF",
                    parkingArea : 314,
                    startTime : "2018-02-04 00:49:18",
                    endTime :  "2018-02-04 00:54:20"},
                    ...]`
 
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
  
  **Check-in**
----
  *Check-in* of a vehicle in an parking area. Returns unique id of use.

* **URL**

  /parkings/

* **Method:**

  `POST`
  
*  **URL Params**

   None

* **Data Params**

  `{ vehicle : "4567-VDF",
      parkingArea : 314}`

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** `{ id : 1 }`
 
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
  
    **Check-out**
----
  *Check-out* of a vehicle in a parking area. Returns state of the usage.

* **URL**

  /parkings/<id>/endtime

* **Method:**

  `POST`
  
*  **URL Params**

   None

* **Data Params**

  `{ id : 1 }`

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** `{ mss : "Result OK" }`
 
* **Error Response:**

  * **Code:** 404 NOT FOUND <br />
    **Content:** `{ error : "User doesn't exist" }`

  OR

  * **Code:** 401 UNAUTHORIZED <br />
    **Content:** `{ error : "You are unauthorized to make this request." }`

* **Sample Call:**

  ```javascript
    $.ajax({
      url: "/parkings/<id>/endtime",
      dataType: "json",
      type : "GET",
      success : function(r) {
        console.log(r);
      }
    });
  ```
