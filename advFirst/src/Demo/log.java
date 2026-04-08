package Demo;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class log {		
		@Before("execution(* Demo.Student.show(..))")
		public void beforeShow() {
			System.out.println("Printing student info:");
		}
}
