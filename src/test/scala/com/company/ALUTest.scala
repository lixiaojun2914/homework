package com.company

import chisel3._
import chisel3.tester._
import chisel3.tester.RawTester.test

object ALUTest extends App{
    def alu(a: Int, b: Int, op: Int): Int = {
        op match {
            case 1 => a + b
            case 2 => a - b
            case 3 => a & b
            case 4 => a | b
            case 5 => a ^ b
            case 6 => b
            case 7 => a >>> 1
            case _ => -123
        }
    }
    val randArgs = Seq.fill(100)(scala.util.Random.nextInt)
    test(new ALU(32)) {c =>
//        for (fun <- 1 to 7){
        for (fun <- 1 to 7){
            for (a <- randArgs){
                for(b <- randArgs){
                    c.io.op.poke(fun.U)
                    c.io.a.poke(a.S)
                    c.io.b.poke(b.S)
                    c.clock.step(1)
                    val aaa = alu(a, b, fun)
                    c.io.y.expect(aaa.S)
                }
            }
        }
    }
}
