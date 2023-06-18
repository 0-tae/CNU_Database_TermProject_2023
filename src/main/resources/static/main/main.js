
const popup_form = document.getElementById("popup_background");
const register_button =document.getElementById("button_register");
const login_button =document.getElementById("button_login");
const cancle_button=document.getElementById("button_cancle");
const login_zone=document.getElementById("zone_login");
const box_printZone=document.getElementById("box_printZone");
const box_reserveZone=document.getElementById("box_reserveZone");
const box_returnZone=document.getElementById("box_returnZone");
const box_previousRentZone=document.getElementById("box_previousRentZone");
const box_tmiZone=document.getElementById("box_tmiZone");
const button_search=document.getElementById("button_search");
const button_previous =document.getElementById("button_previous");
const button_info1=document.getElementById("button_info1");
const button_info2=document.getElementById("button_info2");
const button_info3=document.getElementById("button_info3");


let check_entire=document.getElementById("entire");
let check_electric=document.getElementById("electric");
let check_small=document.getElementById("small");
let check_huge=document.getElementById("huge");
let check_suv=document.getElementById("suv");
let check_hop=document.getElementById("hop");
let date_rentStart=document.getElementById("date_rentStart")
let date_rentEnd=document.getElementById("date_rentEnd")

let check_boxes=[check_entire,
                                check_small,check_huge,
                                check_suv,check_hop]

let username=null;

function register_exit() {
    popup_form.className = "hidableClass";
}

function register_popup() {
    popup_form.classList.remove("hidableClass");
}

function login(){
    let request={}
    request["cno"]=document.getElementById("id_value").value;
    request["passwd"]=document.getElementById("pwd_value").value;

    $.ajax({
        url: "/main/login",
        type: 'POST',
        dataType: 'text', // 돌려받을 타입, text로 받으면 JSON.parse로 해결한다.
        contentType: "application/json", // 전해줄 타입
        data: JSON.stringify(request), // 일단 json을 String으로 보낸다
        async : true,
        headers: {
            "X-Requested-With": "XMLHttpRequest"
        },
        success: function(data){
            username=request["cno"];
            session_check(); // 로그인 이후 세션 체크
        }
    });
}

function search(){
    box_printZone.innerHTML = "";
    check_boxes.forEach(function (box){
        if(check_boxes[0].checked && box.name!=="entire"){
            return;
        } else if(box.checked){ //
            let request={};
            let date=new Date();
            let today = date.getFullYear()+"-"+
                String(date.getMonth()+1).padStart(2,"0")+"-"
                + String(date.getDate()).padStart(2,"0");

            request["startDate"]=date_rentStart.value
            request["endDate"]=date_rentEnd.value
            request["vehicleType"]=box.name

            if(request["startDate"]<today
            || request["endDate"]<today
            || request["startDate"]>request["endDate"]){
                alert("시간 설정을 확인해 주세요.")
                return;
            }

            $.ajax({
                url: "/rent/search",
                type: 'GET',
                dataType: 'text', // 돌려받을 타입, text로 받으면 JSON.parse로 해결한다.
                data: {startDate : date_rentStart.value,
                        endDate : date_rentEnd.value,
                        vehicleType : box.name},
                async : true,
                success: function(data){
                    console.log(data);
                    let givenData=JSON.parse(data);
                    print_availableRentCar(givenData,date_rentStart.value,date_rentEnd.value);
                }
            });
        }
    })
}

function reserve(e){
    let target=e.target;
    let request={};
    request["licensePlateNo"]=target.id;
    request["startDate"]=target.getAttribute("startDate");
    request["endDate"]=target.getAttribute("endDate");
    request["cno"]=username;

    $.ajax({
        url: "/reserve/save",
        type: 'POST',
        dataType: 'text', // 돌려받을 타입, text로 받으면 JSON.parse로 해결한다.
        contentType: "application/json", // 전해줄 타입
        data: JSON.stringify(request),
        async : true,
        success: function(data){
            let givenData=JSON.parse(data);
            print_reservationList(givenData);
            e.target.removeEventListener("click",reserve);
            box_printZone.removeChild(e.target.parentNode);
        }
    });
}
function cancel(e){
    const target=e.target;
    let request={};
    request["licensePlateNo"]=target.id;
    request["startDate"]=target.getAttribute("startDate");
    request["endDate"]=target.getAttribute("endDate");
    request["cno"]=username;

    $.ajax({
        url: "/reserve/cancel",
        type: 'POST',
        dataType: 'text', // 돌려받을 타입, text로 받으면 JSON.parse로 해결한다.
        contentType: "application/json", // 전해줄 타입
        data: JSON.stringify(request),
        async : true,
        success: function(data){
            e.target.removeEventListener("click",cancel);
            box_reserveZone.removeChild(e.target.parentNode);
            search(); // refresh
        }
    });
}


function update_RentalList(){
    $.ajax({
        url: "/rent/save",
        type: 'POST',
        dataType: 'text', // 돌려받을 타입, text로 받으면 JSON.parse로 해결한다.
        contentType: "application/json", // 전해줄 타입
        async : true,
        success: function(){
            console.log("RentalList Updated");
        }
    });
}

function print_rentalListAll(){
    $.ajax({
        url: "/rent/readAll",
        type: 'GET',
        dataType: 'text', // 돌려받을 타입, text로 받으면 JSON.parse로 해결한다.
        contentType: "application/json", // 전해줄 타입
        async : true,
        success: function(data){
            box_returnZone.innerHTML="";
            const rentalList=JSON.parse(data);
            rentalList.forEach(rentCar => print_rentalList(rentCar))
        }
    });
}

function print_rentalList(rentalJSON){
    const myElement = $(
        `<span style="display: inline-block; border: 1px solid black; margin: 5px">
                <p>차량번호: ${rentalJSON.licensePlateNo}</p>
                <p>모델명: ${rentalJSON.modelName}</p>
                <p>대여 시작일 : ${rentalJSON.dateRented}</p>
                <p>반납 예정일: ${rentalJSON.dateDue}</p>
                <p>예상 결제 금액: ${rentalJSON.expectedPayment}</p>
                <p>옵션: ...</p>
                <button id="${rentalJSON.licensePlateNo}/Rent" startDate=${rentalJSON.startDate} endDate=${rentalJSON.endDate}>반납 및 결제하기</button>
            </span>`
    );
    box_returnZone.appendChild(myElement[0]);
    document.getElementById(rentalJSON.licensePlateNo+"/Rent")
        .addEventListener("click",update_previousRentalList);
}

function update_previousRentalList(e){
    $.ajax({
        url: "/rent/return",
        type: 'POST',
        dataType: 'text', // 돌려받을 타입, text로 받으면 JSON.parse로 해결한다.
        data: e.target.id,
        async : true,
        success: function(data){
            console.log(data);
            e.target.removeEventListener("click",update_previousRentalList);
            box_returnZone.removeChild(e.target.parentNode);
        }
    });
}

function print_previousRentalList(previousJSON){
    const myElement = $(
        `<span style="display: inline-block; border: 1px solid black; margin: 5px">
                <p>차량번호: ${previousJSON.rentCar.licensePlateNo}</p>
                <p>모델명: ${previousJSON.rentCar.carModel.modelName}</p>
                <p>차종: ${previousJSON.rentCar.carModel.vehicleType}</p>
                <p>대여 기간 : ${previousJSON.dateRented} ~ ${previousJSON.dateReturned}</p>
                <p>결제 금액: ${previousJSON.payment}</p>
        </span>`
    );
    box_previousRentZone.appendChild(myElement[0]);
}
function print_previousRentalListAll(){
    $.ajax({
        url: "/previous/readAll",
        type: 'GET',
        dataType: 'text', // 돌려받을 타입, text로 받으면 JSON.parse로 해결한다.
        contentType: "application/json", // 전해줄 타입
        async : true,
        success: function(data){
            box_previousRentZone.innerHTML="";
            const dataArray=JSON.parse(data);
            dataArray.forEach(data => print_previousRentalList(data));
        }
    });
}

function print_reservationListAll(){
    $.ajax({
        url: "/reserve/readAll",
        type: 'GET',
        dataType: 'text', // 돌려받을 타입, text로 받으면 JSON.parse로 해결한다.
        contentType: "application/json", // 전해줄 타입
        async : true,
        success: function(data){
            box_reserveZone.innerHTML="";
            const dataArray=JSON.parse(data);
            dataArray.forEach(data => print_reservationList(data))
        }
    });
}


function print_reservationList(reservationJSON){
        const myElement = $(
            `<span style="display: inline-block; border: 1px solid black; margin: 5px">
                <p>차량번호: ${reservationJSON.rentCar.licensePlateNo}</p>
                <p>모델명: ${reservationJSON.rentCar.carModel.modelName}</p>
                <p>차종: ${reservationJSON.rentCar.carModel.vehicleType}</p>
                <p>대여 시작일 : ${reservationJSON.startDate}</p>
                <p>반납 예정일: ${reservationJSON.endDate}</p>
                <p>예약일: ${reservationJSON.reserveDate}</p>
                <button id="${reservationJSON.rentCar.licensePlateNo}/R" startDate=${reservationJSON.startDate} endDate=${reservationJSON.endDate}>취소하기</button>
            </span>`
        );
        box_reserveZone.appendChild(myElement[0]);
        document.getElementById(reservationJSON.rentCar.licensePlateNo+"/R")
        .addEventListener("click",cancel);
}

function print_availableRentCar(rentCarsJSON,startDate,endDate){
    rentCarsJSON.forEach(data => {
        const myElement = $(
            `<span style="display: inline-block; border: 1px solid black; margin: 5px">
                <p>차량번호: ${data.licensePlateNo}</p>
                <p>모델명: ${data.carModel.modelName}</p>
                <p>차종: ${data.carModel.vehicleType}</p>
                <p>좌석 수: ${data.carModel.numberOfSeats}</p>
                <p>사용연료: ${data.carModel.fuel}</p>
                <p>일당 결제 금액: ${data.carModel.rentRatePerDay}</p>
                <p>옵션: ...</p>
                <button id="${data.licensePlateNo}" startDate=${startDate} endDate=${endDate}>예약하기</button>
            </span>`
        );
        box_printZone.appendChild(myElement[0]);
        const addedButton=document.getElementById(data.licensePlateNo);
        addedButton.addEventListener("click",reserve);
    });
}

function info(e){
    let infoType=e.target.getAttribute("infoType");
    let url;
    switch (parseInt(infoType)){
        case 1:
            url="/total/CarModelPerRental";
            break;
        case 2:
            let input_year=document.getElementById("input_year");
            let input_modelName=document.getElementById("input_modelName");
            url=`/total/PaymentAndRentalPerYear?year=${input_year.value}&&modelName=${input_modelName.value}`;
            break;
        case 3:
            url="/total/VIPCustomer";
            break;
    }

    $.ajax({
        url: url,
        type: 'GET',
        dataType: 'text', // 돌려받을 타입, text로 받으면 JSON.parse로 해결한다.
        contentType: "application/json", // 전해줄 타입
        async : true,
        success: function(data){
            const dataArray=JSON.parse(data);
            switch (parseInt(infoType)){
                case 1:
                    print_info1(dataArray)
                    break;
                case 2:
                    print_info2(dataArray)
                    break;
                case 3:
                    print_info3(dataArray)
                    break;
            }
        }
    });
}

function print_info1(infoJSONArray){
    box_tmiZone.innerHTML = "";
    const tableTitle = $(`
        <tr>
            <th>모델명</th>
            <th>대여 횟수</th>
            <th>총 매출액</th>
        </tr>
    `);
    box_tmiZone.appendChild(tableTitle[0]);
    infoJSONArray.forEach(info => {
        console.log(info);

        const myElement = $(
        `<tr>
            <td>${info.modelname}</td>
            <td>${info.rentalcount}</td>
            <td>${info.payments}</td>
        </tr>`
        );
        box_tmiZone.appendChild(myElement[0]);
    });
}

function print_info2(infoJSONArray){
    box_tmiZone.innerHTML = "";
    const tableTitle = $(`
        <tr>
            <th>모델명</th>
            <th>대상 월</th>
            <th>빌린 횟수</th>
            <th>매출액</th>
        </tr>
    `);
    box_tmiZone.appendChild(tableTitle[0]);
    infoJSONArray.forEach(info => {
        console.log(info);

        const myElement = $(
            `<tr>
            <td>${info.car}</td>
            <td>${info.rentdate}</td>
            <td>${info.total_Rentaled}</td>
            <td>${info.total_Payment}</td>
        </tr>`
        );
        box_tmiZone.appendChild(myElement[0]);
    });
}

function print_info3(infoJSONArray){
    box_tmiZone.innerHTML = "";
    const tableTitle = $(`
        <tr>
            <th>고객명</th>
            <th>총 사용 금액</th>
            <th>순위</th>
        </tr>
    `);
    box_tmiZone.appendChild(tableTitle[0]);
    infoJSONArray.forEach(info => {
        console.log(info);
        const myElement = $(
            `<tr >
            <td>${info.cno}</td>
            <td>${info.total_Payment}</td>
            <td>${info.rank}</td>
        </tr>`
        );
        box_tmiZone.appendChild(myElement[0]);
    });
}

function logout(){
    $.ajax({
        url: "/session_destroy",
        type: 'POST',
        async : true,
        headers: {
            "X-Requested-With": "XMLHttpRequest"
        },
        success: function(session){
            console.log(session)
            location.reload();
        }
    });
}

function session_check(event){
    $.ajax({
        url: "/session_check",
        type: 'POST',
        async : true,
        headers: {
            "X-Requested-With": "XMLHttpRequest"
        },
        success: function(session){
            console.log(session)
            if(session.cno!==null) {
                login_zone.innerHTML = "";
                const content = $(`<p>로그인 정보: ${session.cno}, sessionId: ${session.sessionId}</p>`);
                const button = $(`<button id="logout_button">로그아웃</button>`);

                login_zone.appendChild(content[0]);
                login_zone.appendChild(button[0]);
                document.getElementById("logout_button").addEventListener("click", logout);

                username = session.cno;
                return true;
            }
        }
    });

    return false;
}

function load(){
    session_check();
    print_reservationListAll();
    update_RentalList();
    print_rentalListAll();
}

login_button.addEventListener("click", login);
register_button.addEventListener("click", register_popup);
cancle_button.addEventListener("click",register_exit);
button_search.addEventListener("click",search);
button_previous.addEventListener("click",print_previousRentalListAll);
document.addEventListener("DOMContentLoaded", load);
button_info1.addEventListener("click",info);
button_info2.addEventListener("click",info);
button_info3.addEventListener("click",info);