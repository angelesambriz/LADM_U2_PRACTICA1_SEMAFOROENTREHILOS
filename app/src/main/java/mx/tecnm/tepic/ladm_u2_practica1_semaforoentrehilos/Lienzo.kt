package mx.tecnm.tepic.ladm_u2_practica1_semaforoentrehilos

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.View

class Lienzo (p: MainActivity): View(p) {
    var cont=0
    var cont2=0

    var temcar1=true
    var temcar2 =true

    var contsem = 0
    var contsem2 = 0

    var activar=true
    var acti=true

    override  fun onDraw(canvas: Canvas){
        var hiloH =HiloCarHorizontal(this)
            hiloH.start()


        var hiloV =HiloCarVertical(this)
        if(activar) {
            hiloV.start()
        }

        var hilosem=HiloSemaforos(this)
        if(acti) {
            hilosem.start()
            acti=false
        }

        invalidate()
        super.onDraw(canvas)
        val p = Paint()

        canvas.drawColor(Color.rgb(129, 212, 250))

        //CARRETERA HORIZONTAL-------------------------
        p.color=Color.GRAY
        canvas.drawRect(0f, 1200f, 1300f, 850f, p)
        p.color=Color.WHITE
        p.strokeWidth=10f
        //IZQUIERDA
        canvas.drawLine(40f,1020f,100f,1020f,p)
        canvas.drawLine(170f,1020f,240f,1020f,p)
        //DERECHA
        canvas.drawLine(790f,1020f,860f,1020f,p)
        canvas.drawLine(930f,1020f,1000f,1020f,p)

        //CARRETERA VERTICAL----------------------------
        p.color=Color.GRAY
        canvas.drawRect(350f, 2200f, 700f, 0f, p)
        p.color=Color.WHITE
        p.strokeWidth=10f
        //ARRIBA
        canvas.drawLine(533f,720f,533f,650f,p)
        canvas.drawLine(533f,510f,533f,440f,p)
        canvas.drawLine(533f,310f,533f,240f,p)
        canvas.drawLine(533f,110f,533f,40f,p)
        //ABAJO
        canvas.drawLine(533f,1320f,533f,1390f,p)
        canvas.drawLine(533f,1520f,533f,1590f,p)
        canvas.drawLine(533f,1720f,533f,1790f,p)
        canvas.drawLine(533f,1920f,533f,1990f,p)

        //CARRO IZQ A DERECHA ---------------
        p.color = Color.rgb(236, 64, 122 )
        canvas.drawRect(-180+cont.toFloat(), 1160f, -40+cont.toFloat(), 1060f, p)
        //CARRO DERECHA A IZQ
        p.color = Color.rgb(66, 165, 245 )
        canvas.drawRect(1100f-cont, 970f, 1250f-cont, 870f, p)
        //CARRO DE ARRIBA A ABAJO
        p.color = Color.rgb(204, 0, 255 )
        canvas.drawRect(400f, -460f+cont2, 500f, -300f+cont2, p)
        //CARRO DE ABAJO A ARRIBA
        p.color = Color.rgb(156, 204, 101 )
        canvas.drawRect(560f, 2250f-cont2, 660f, 2100f-cont2, p)

        //SEMAFORO 1-----------------------------------------------------------------
        p.color = Color.WHITE
        canvas.drawRect(80f, 800f, 300f, 310f, p)

        p.color = Color.rgb(204, 51, 0 )
        canvas.drawCircle(190f, 400f, 70f, p)

        p.color=Color.rgb(204, 204, 0)
        canvas.drawCircle(190f, 550f, 70f, p)

        p.color=Color.rgb(0, 204, 51)
        canvas.drawCircle(190f, 700f, 70f, p)

        if(contsem in 160..180) {
            p.color = Color.RED
            canvas.drawCircle(190f, 400f, 70f, p)
        }
        else if (contsem in 120..159){
        p.color=Color.YELLOW
        canvas.drawCircle(190f, 550f, 70f, p)}
        else if (contsem in 181..200){
            p.color=Color.YELLOW
            canvas.drawCircle(190f, 550f, 70f, p)}
        else {
            p.color=Color.GREEN
            canvas.drawCircle(190f, 700f, 70f, p)
        }
        //---------------------------------------------------------------------------

      //SEMAFORO 2-------------------------------------------------------------------
        p.color=Color.WHITE
        canvas.drawRect(960f, 1750f, 740f, 1250f, p)
        p.color=Color.rgb(204, 51, 0 )
        canvas.drawCircle(850f, 1350f, 70f, p)

        p.color=Color.rgb(204, 204, 0)
        canvas.drawCircle(850f, 1500f, 70f, p)

        p.color=Color.rgb(0, 204, 51)
        canvas.drawCircle(850f, 1650f, 70f, p)

        if(contsem2 in 48..95) {
        p.color=Color.RED
            canvas.drawCircle(850f, 1350f, 70f, p)}
        else if (contsem2 in 37..47){
        p.color=Color.YELLOW
        canvas.drawCircle(850f, 1500f, 70f, p)}
        else if (contsem2 in 96..117){
            p.color=Color.YELLOW
            canvas.drawCircle(850f, 1500f, 70f, p)
        }
        else{
        p.color=Color.GREEN
        canvas.drawCircle(850f, 1650f, 70f, p)}

        //--------------------------------------------------------------------------
    }
}

class HiloSemaforos(act: Lienzo) : Thread(){
    var et =act
    override fun run(){
    super .run()
        while (true) {
            et.run {
                if (true) {
                    sleep(40)
                    et.contsem++
                    et.contsem2++
                    et.invalidate()
                    if (contsem == 200) {
                        contsem = 1
                    }
                    if (contsem2 == 200) {
                        contsem2 = 1
                    }
                }
            }
            sleep(40)
        }
    }
}

class HiloCarHorizontal(act: Lienzo) :Thread() {
    var et = act
    override fun run() {
        super.run()
        while (et.temcar1) {
            if (et.cont == 390) {
                et.temcar1=false
            }else if(et.cont>=1400){
                et.temcar2=true
                et.cont=0
            }
            et.cont++
            sleep(2000)
        }
    }
}

class HiloCarVertical(act: Lienzo) : Thread() {
    var et = act
    override fun run() {
        super.run()
        while (et.temcar2) {
            if (et.cont2 == 2500) {
                et.activar = false
                et.cont2 = 0
            }
                if(et.cont2==1800){
                    et.temcar1=true
            }
            if (et.temcar1 && et.cont==800){
                et.temcar2=false
                sleep(600)
                et.activar=true
            }
            et.cont2++
            sleep(2000)
        }

    }

}




