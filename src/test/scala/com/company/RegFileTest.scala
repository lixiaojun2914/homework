package com.company

import chisel3._
import chisel3.tester._
import chisel3.tester.RawTester.test

object RegFileTest extends App{
  test(new RegFile(2) ) { c =>
    def readExpect(addr: Int, value: Int, port: Int = 0): Unit = {
      c.io.raddr(port).poke(addr.U)
      c.io.rdata(port).expect(value.U)
    }
    def write(addr: Int, value: Int): Unit = {
      c.io.wen.poke(true.B)
      c.io.wdata.poke(value.U)
      c.io.waddr.poke(addr.U)
      c.clock.step(1)
      c.io.wen.poke(false.B)
    }
    // everything should be 0 on init
    for (i <- 0 until 32) {
      readExpect(i, 0, port = 0)
      readExpect(i, 0, port = 1)
    }

    // write 5 * addr + 3
    for (i <- 0 until 32) {
      write(i, 5 * i + 3)
    }

    // check that the writes worked
    for (i <- 0 until 32) {
      readExpect(i, if (i == 0) 0 else 5 * i + 3, port = i % 2)
    }
  }
}
