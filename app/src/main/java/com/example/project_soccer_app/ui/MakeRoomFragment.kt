package com.example.project_soccer_app.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import com.example.project_soccer_app.R

// xml 사용하지 않는 jetpack compose 시범운영
class MakeRoomFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view =  ComposeView(requireContext()).apply {
            setContent {
                Test()
            }
        }

        return view
    }


}

@Composable
fun Test(){
    var scrollState = rememberScrollState()

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Column(
            Modifier
                .verticalScroll(scrollState)
                .padding(vertical = 20.dp, horizontal = 20.dp)) {
            Step(1, 200,"시간/장소 설정")
            Step(2, 500,"포메이션 설정")
        }
    }
}

@Composable
fun Step(type:Int, extraDp: Int, name: String) {
    var expanded = remember { mutableStateOf(false) }

    var extraPadding = animateDpAsState(
        if (expanded.value) extraDp.dp else 80.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessLow
        )
//        animationSpec = spring(
//            dampingRatio = Spring.DampingRatioMediumBouncy,
//            stiffness = Spring.StiffnessLow
//        )
    )

    Surface(
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        Column(
            Modifier
                .border(width = 2.dp, color = Color.Black, shape = RoundedCornerShape(20.dp))
                .height(extraPadding.value)
                .padding(vertical = 10.dp, horizontal = 10.dp)) {
            Row(
                modifier = Modifier
                    .background(color = Color.White), verticalAlignment = Alignment.CenterVertically
            )
            {
                Row(
                    Modifier
                        .weight(1f)
                        .padding(10.dp)) {
                    Text(type.toString(), fontSize = 20.sp)
                }
                Row(
                    Modifier
                        .weight(2f)
                        .padding(10.dp)) {
                    Text(name, fontSize = 20.sp)
                }
                Column(
                    Modifier
                        .weight(1f)
                        .padding(10.dp)
                        .clickable { expanded.value = !expanded.value }, horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(">", fontSize = 20.sp)
                }
            }
            if(expanded.value == true){
                if(type == 1){

                }
                else if(type == 2){
                    FormationDetail()
                }
            }
        }
    }
}

@Composable
fun TimeLocationDetail(){

}

@Composable
fun FormationDetail(){
    Surface(
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ){
        Image(painter = painterResource(id = R.drawable.soccer_field), contentDescription = "대체 텍스트", modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight())
        Column(modifier = Modifier.padding(vertical = 20.dp, horizontal = 20.dp)) {
            Row(Modifier.weight(1f)){
                //빈 공간
            }
            Row(Modifier.weight(1f)){
                HorizontalFormation(4)
            }
            Row(Modifier.weight(1f)){
                HorizontalFormation(4)
            }
            Row(Modifier.weight(1f)){
                //빈 공간
            }
            Row(Modifier.weight(1f)){
                HorizontalFormation(4)
            }
            Row(Modifier.weight(1f)){
                HorizontalFormation(4)
            }
            Row(Modifier.weight(1f)){
                //빈 공간
            }

        }
    }
}

@Composable
fun HorizontalFormation(many: Int){
    Row {
        for(i in 0 until many){
            Row(Modifier.weight(1f)){
                PlayerIcon()
            }
        }
    }
}

@Composable
fun PlayerIcon(){
        Image(painter = painterResource(id = R.drawable.player1), contentDescription = "대체 텍스트", modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight())
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Test()
}