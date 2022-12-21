package com.example.vgnmanage;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ConectionWithNet {
    String Url1 = "http://efikhatar.ir/";//adrese damanesitebe alave/

    //    veganmanage esm data base
    @Multipart
    @POST("veganmanage1.php")
    Call<String> uploadditails(
            //filefoodimagename kilid ya esme filemon ke server az in tarigh shenasaii kone va file.getName() name file
            @Part MultipartBody.Part file  // fili ke chand parte esm va axe
            , @Part("foodname") RequestBody name
            , @Part("fooddescription") RequestBody description
            , @Part("foodhealthy") RequestBody healthypercent
            , @Part("fooddelicios") RequestBody deliciospercent
            , @Part("foodvegan") RequestBody vegannumber
//<?php
////in insert mikone va hamono barmigardone va mige insert shode ya na
//// aval bayad az data base ya php admine site ye data base sakht badesham table
//                    if($_SERVER['REQUEST_METHOD']=='POST'){
//        include_once("config.php");
//
//        $originalImgName= $_FILES['filefoodimagename']['name'];
//        $foodimage= $_FILES['filefoodimagename']['tmp_name'];
//
//        $foodname=$_POST['foodname'];
//        $fooddescription=$_POST['fooddescription'];
//        $foodhealthy=$_POST['foodhealthy'];
//        $fooddelicios=$_POST['fooddelicios'];
//        $foodvegan=$_POST['foodvegan'];
//        $foodhealthypercnt=(int)$foodhealthy;
//        $fooddeliciospercnt=(int)$fooddelicios;
//        $foodveganint=(int)$foodvegan;
//
//        $folder="veganimage/";
//
//        $url = "http://efikhatar.ir/".$folder.$originalImgName; //update path as per your directory structure
//
//        if(move_uploaded_file($foodimage,$folder.$originalImgName)){
//            $query = "INSERT INTO veganmanage (name,description,healthy,delicios  ,vegan,imageurl) VALUES
//            ('$foodname','$fooddescription','$foodhealthypercnt','$fooddeliciospercnt','$foodveganint','$url')";
//            if(mysqli_query($con,$query)){//in yani agar insert shod edame bede
//                $query= "SELECT * FROM veganmanage WHERE imageurl='$url'";
//                $result= mysqli_query($con, $query);
//                //data objecti ke insert kardimo mikhaim
//                $emparray = array();
//                if(mysqli_num_rows($result) > 0){
//                    while ($row = mysqli_fetch_assoc($result)) {
//                        $emparray[] = $row;
//                    }
//                    echo json_encode(array("status" => "true","message" => "Successfully file added!" , "data" => $emparray) );
//                }
//                else{
//                    echo json_encode(array("status" => "false","message" => "Failed1!") );
//                }
//            }else{
//                echo json_encode(array( "status" => "false","message" => "Failed2!") );
//            }
//        }
//        else{
//            echo json_encode(array( "status" => "false","message" => "Failed3!") );
//        }
//    }
//
//?>
    );

    @Multipart
//    @POST("sendfilter_getfoods.php")
    @POST("sendfilter_getfoods.php")
    Call<String> uploadfiltersandgetfoods(
// faghat baraye ferestadan yekeri string ha
            @Part("filterfoodsname") RequestBody name
            , @Part("filterdescriptions") RequestBody description
            , @Part("filterfoodshealthy") RequestBody healthypercent
            , @Part("filterfooddelicios") RequestBody deliciospercent
            , @Part("filterfoodvegan") RequestBody vegannumber
            , @Part("filterlastfoodid") RequestBody lastfoodid

//<?php
// // aval bayad az data base ya php admine site ye data base sakht badesham table
//                     if($_SERVER['REQUEST_METHOD']=='POST'){
//         include_once("config.php");

//         $foodname="";
//         $fooddescription="";
//         $foodhealthypercnt=0;
//         $fooddeliciospercnt=0;
//         $foodveganint=0;
//         $foodlastid=0;

//         $foodname=$_POST['filterfoodsname'];
//         $fooddescription=$_POST['filterdescriptions'];
//         $foodhealthy=$_POST['filterfoodshealthy'];
//         $fooddelicios=$_POST['filterfooddelicios'];
//         $foodvegan=$_POST['filterfoodvegan'];
//         $foodhealthypercnt=(int)$foodhealthy;
//         $fooddeliciospercnt=(int)$fooddelicios;
//         $foodveganint=(int)$foodvegan;

//         $foodlastid=(int)$_POST['filterlastfoodid'];


//         $desarray = array();
//         function changestrtostrs($str) {
//                 $desarrayf = array();
//         $desarrayf=explode(".*", $str);
// // baraye joda kardan araye ha az ham ke ghablan beyneshon alamat gozashtim
// //$pizza  = "piece1 piece2 piece3 piece4 piece5 piece6";
// //$pieces = explode(" ", $pizza);
// //echo $pieces[1]; // piece2

//         return  $desarrayf;
//     }

//         $desarray=changestrtostrs($fooddescription);
// //yek ravesh bara inke yek listi ro tahvil bedim in mishe

//         function get_desvriptio_and_changetodb($des)    {
//                 $dbstring="";

//         //  for(int i=0;i<count($des);i++)
//         //  {
//         //              $dbstring = $dbstring . " AND fooddescription LIKE '%"
//         //                 . $des[i] . "%'";
//         //      }
//         foreach($des as $index)
//         //chon ke araye dadim goftim be aza har object jodakon
//         {
//             //  $dbstring = $dbstring . " AND fooddescription LIKE '%"
//             //    . $des[i] . "%'";
//             $dbstring = $dbstring . " AND description LIKE '%"
//                 .$index."%'";
//         }

//         return  $dbstring;
//             }

//         $desmassage="";
//         $desmassage=get_desvriptio_and_changetodb($desarray);

//         function databool($b) {
//                 $massagedataboole = "";
//         if ($b==1) {// faghat vegan
//             $massagedataboole = " AND vegan =1 ";
//         }
//         return $massagedataboole;
// 	 }

//         $url = ""; //update path as per your directory structure
//         $tablename="veganmanage";

//         $query = "SELECT * FROM veganmanage
//         WHERE name LIKE '%".$foodname."%'
//         AND id>".(string)$foodlastid."
//         AND healthy>".(string)$foodhealthypercnt."
//         AND delicios>".(string)$fooddeliciospercnt
//     //chon ke hame hadeghal delicios ya healthy ro vared kardan va to barname ino khastim bishtaresho mikhaim

//                 //   ."AND vegan=".(string)$foodveganint
//                 .databool($foodveganint)
//                 .$desmassage." LIMIT 6";
//         // 	." AND description LIKE '%".$desmassage."%'";

//         // 	LIMIT 6";
//         // 	$query = "SELECT * FROM veganmanage";


//         // if(mysqli_query($con, $query)){
//         $result= mysqli_query($con, $query);

//         $emparray = array();
//         if(mysqli_num_rows($result) > 0){
//             while ($row = mysqli_fetch_assoc($result)) {
//                 $emparray[] = $row;
//             }
//             echo json_encode(array("status" => "true","message" => "Successfully file geted!" , "data" => $emparray ) );
//         }
//         else{
//             echo json_encode(array("status" => "false","message" => "Failed1!") );
//         }
//         // }
//         // else{
//         //     echo json_encode(array( "status" => "false","message" => "Failed2!") );
//         // }
//     }
// 		else{
//         echo json_encode(array( "status" => "false","message" => "Failed3!") );
//     }


// ?>
    );
}
