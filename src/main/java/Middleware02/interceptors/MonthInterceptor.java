package Middleware02.interceptors;

import Middleware02.entities.Month;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class MonthInterceptor implements HandlerInterceptor {

    public static final Logger logger = LoggerFactory.getLogger(MonthInterceptor.class);

    public static List<Month> monthsList = new ArrayList<>(Arrays.asList(
            new Month(1, "January", "Gennaio", "Januar"),
            new Month(2, "February", "Febbraio", "Februar"),
            new Month(3, "March", "Marzo", "März"),
            new Month(4, "April", "Aprile", "April"),
            new Month(5, "May", "Maggio", "Mai"),
            new Month(6, "June", "Giugno", "Juni")
    ));

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String monthNumber = request.getHeader("Month-Number");
        if(request.getHeader("Month-Number") == null){
            response.setStatus(400); //400 = Bad Request
            System.out.println("Bad Request! Header Month-Number is null!");
            return false;
        }
        //altrimenti esegui questa logica:
        int monthNumberInt = Integer.parseInt("monthNumber");
        Optional<Month> month = monthsList.stream().filter(singleMonth -> {
            return singleMonth.getMonthNumber() == monthNumberInt;
        }).findFirst();
        if (month.isPresent()){
            request.setAttribute("MonthInterceptor-month", month.get());
            return true;
        }
        request.setAttribute("MonthInterceptor-month", new Month (0, "nope", "nope", "nope"));
        return true;
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           @Nullable ModelAndView modelAndView) throws Exception {
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                @Nullable Exception ex) throws Exception {
    }
}
