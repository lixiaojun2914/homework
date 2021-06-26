package com.company

import chisel3._
import chisel3.util._
import com.company.Types._

object Types {
    val nop :: add :: sub :: and :: or :: xor :: ld :: shr :: Nil = Enum(8)
}

class ALU(size: Int) extends Module {
    val io = IO(new Bundle {
        val op = Input(UInt(4.W))
        val a = Input(SInt(size.W))
        val b = Input(SInt(size.W))
        val y = Output(SInt(size.W))
    })

    val op = io.op
    val a = io.a
    val b = io.b
    val res = WireDefault(0.S(size.W))

    switch(op){
        is(add){
            res := a + b
        }
        is(sub){
            res := a - b
        }
        is(and){
            res := a & b
        }
        is(or){
            res := a | b
        }
        is(xor){
            res := a ^ b
        }
        is(ld){
            res := b
        }
        is(shr) {
            res := (a >> 1).asSInt() & 0x7fffffff.S
        }
    }

    io.y := res
}