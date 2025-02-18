package com.group.libraryapp.calculator

import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class CalculatorJunitTest {

  @Test
  fun addTest() {

    // given
    val calculator = Calculator(5)
    
    // when
    calculator.add(3)
    
    // then
    assertThat(calculator.number).isEqualTo(8)
    
  }
  
  @Test
  fun minusTest() {

    // given
    val calculator = Calculator(5)

    // when
    calculator.minus(3)

    // then
    assertThat(calculator.number).isEqualTo(2)

  }
  
  @Test
  fun multiplyTest() {

    // given
    val calculator = Calculator(5)

    // when
    calculator.multiply(3)

    // then
    assertThat(calculator.number).isEqualTo(15)

  }
  
  @Test
  fun divideTest() {

    // given
    val calculator = Calculator(5)

    // when
    calculator.divide(1)
    
    // then
    assertThat(calculator.number).isEqualTo(5)

  }
  
  @Test
  fun divideZeroTest() {

    // given
    val calculator = Calculator(5)

    // when
//    val message = assertThrows<IllegalArgumentException> {
//      calculator.divide(0)
//    }.message
    
    assertThrows<IllegalArgumentException> {
      calculator.divide(0)
    }.apply {
      assertThat(message).isEqualTo("0으로 나눌 수 없습니다 !")
    }
    
  }
  
}