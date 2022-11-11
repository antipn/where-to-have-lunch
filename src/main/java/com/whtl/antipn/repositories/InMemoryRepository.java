package com.whtl.antipn.repositories;

import com.whtl.antipn.exception.EntityNotFoundException;
import com.whtl.antipn.model.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.*;

@Repository
public class InMemoryRepository {

    private final Map<Integer, User> mapUsers = new HashMap<>();
    private final Map<Integer, Role> mapRoles = new HashMap<>();
    private final Map<Integer, Restaurant> mapRestaurants = new HashMap<>();
    private final Map<Integer, List<Menu>> mapMenus = new HashMap<>();

    private final Map<LocalDate, Map<Integer, List<Menu>>> mapMenuByDates = new HashMap<>();  //map <Date map <resId, List<Menu>>

    private final Map<LocalDate, HashSet<Vote>> mapVotes = new HashMap<>(); //map LocalDate and Set his votes

    {

        //votes
        mapVotes.put(LocalDate.of(2022, 11, 1), new HashSet<>(Arrays.asList(new Vote(1, 1), new Vote(2, 2))));
        mapVotes.put(LocalDate.of(2022, 11, 2), new HashSet<>(Arrays.asList(new Vote(1, 2), new Vote(2, 2))));
        mapVotes.put(LocalDate.of(2022, 11, 3), new HashSet<>(Arrays.asList(new Vote(1, 1), new Vote(2, 1))));

        //rating
//        mapRestaurantsScoreInner.put(LocalDate.of(2022, 11, 1), 91); //for rest = 1
//        mapRestaurantsScoreInner.put(LocalDate.of(2022, 11, 2), 92);
//        mapRestaurantsScoreInner.put(LocalDate.of(2022, 11, 3), 93);
//        mapRestaurantsScore.put(1, mapRestaurantsScoreInner);
//        mapRestaurantsScoreInner.clear();
//        mapRestaurantsScoreInner.put(LocalDate.of(2022, 11, 1), 81); //for rest = 2
//        mapRestaurantsScoreInner.put(LocalDate.of(2022, 11, 2), 82);
//        mapRestaurantsScoreInner.put(LocalDate.of(2022, 11, 3), 83);
//        mapRestaurantsScore.put(2, mapRestaurantsScoreInner);
//        mapRestaurantsScoreInner.clear();
//        mapRestaurantsScoreInner.put(LocalDate.of(2022, 11, 1), 71); //for rest = 3
//        mapRestaurantsScoreInner.put(LocalDate.of(2022, 11, 2), 72);
//        mapRestaurantsScoreInner.put(LocalDate.of(2022, 11, 3), 73);
//        mapRestaurantsScore.put(3, mapRestaurantsScoreInner);

        //roles
        Role adminRole = new Role(1, "Admin", "Administrating");
        Role userRole = new Role(2, "Admin", "Administrating");

        mapRoles.put(1, adminRole);
        mapRoles.put(2, userRole);

        //users

        User admin = new User(0, Arrays.asList(adminRole));
        User user1 = new User(1, Arrays.asList(userRole));
        User user2 = new User(2, Arrays.asList(userRole));

        mapUsers.put(0, admin);
        mapUsers.put(1, user1);
        mapUsers.put(2, user2);

        // menus


        Menu menu1 = new Menu("Breakfast", 1000.0);
        Menu menu2 = new Menu("Lunch", 2000.0);
        Menu menu3 = new Menu("Dinner", 3000.0);
        List<Menu> menuRestOne = List.of(menu1, menu2, menu3);
        Menu menu4 = new Menu("Breakfast", 2000.0);
        Menu menu5 = new Menu("Lunch", 3000.0);
        Menu menu6 = new Menu("Dinner", 4000.0);
        List<Menu> menuRestTwo = List.of(menu4, menu5, menu6);
        Menu menu7 = new Menu("Breakfast", 3000.0);
        Menu menu8 = new Menu("Lunch", 4000.0);
        Menu menu9 = new Menu("Dinner", 5000.0);
        List<Menu> menuRestThree = List.of(menu7, menu8, menu9);

        mapMenus.put(1, menuRestOne);
        mapMenus.put(2, menuRestTwo);
        mapMenus.put(3, menuRestThree);

        mapMenuByDates.put(LocalDate.of(2022, 11, 1), mapMenus);
        mapMenuByDates.put(LocalDate.of(2022, 11, 2), mapMenus);
        mapMenuByDates.put(LocalDate.of(2022, 11, 3), mapMenus);

        //restaurants

        Restaurant restaurant1 = new Restaurant(1, "Grand Hotel", "Address1", true);
        Restaurant restaurant2 = new Restaurant(2, "Palace", "Address2", true);
        Restaurant restaurant3 = new Restaurant(3, "Astoria", "Address3", true);

        mapRestaurants.put(1, restaurant1);
        mapRestaurants.put(2, restaurant2);
        mapRestaurants.put(3, restaurant3);
    }

    public void showMaps() {//rempopary method for showing in console our data
        //mapRestaurants.forEach((k, v) -> System.out.println(k + " " + v));
        //mapMenus.forEach((k, v) -> System.out.println(k + " " + v));
        //mapUsers.forEach((k, v) -> System.out.println(k + " " + v));
        //mapRoles.forEach((k, v) -> System.out.println(k + " " + v));
        //mapVotes.forEach((k, v) -> System.out.println(k + " " + v));
        //mapRestaurantsScore.forEach((k, v) -> System.out.println(k + " " + v));
    }

    //restaurants
    public List<Restaurant> findAllRestaurants() {
        //System.out.println("Нашли рестораны в базе");
        List<Restaurant> result = new ArrayList<>();
        return mapRestaurants.values().stream().toList();
    }

    public Restaurant findRestaurantById(int id) {
        //System.out.println("Нашли ресторан в базе");
        return mapRestaurants.get(id);
    }

    public Restaurant saveRestaurant(Restaurant restaurant) {
        int id = mapRestaurants.size() + 1;
        if (restaurant.getId() == null) {
            restaurant.setId(id);
        }
        mapRestaurants.put(id, restaurant);
        return mapRestaurants.get(id);

    }

    public boolean deleteRestaurant(int restId) { //discuss it
        return mapRestaurants.remove(restId) != null;
    }

    //rating

    public Map<Integer, Integer> findAllRestaurantsScoreOnToday(LocalDate localDate) {
        Set<Vote> votesOnToday = mapVotes.get(localDate); //votes not found -> exception
        if (votesOnToday == null) {
            throw new EntityNotFoundException("Rating", 0, "There is no rating on date = " + localDate);
        }
        List<Integer> onlyVotesByRestId = new ArrayList<>();
        //receives only votes on today which ones are just List of restId
        votesOnToday
                .forEach(item -> onlyVotesByRestId.add(item.getRestaurantId()));
        //map restId, restId repeat times
        Map<Integer, Integer> scores = new HashMap<>();
        onlyVotesByRestId
                .forEach(item -> scores.put(item, Collections.frequency(onlyVotesByRestId, item)));
        //scores.forEach((k, v) -> System.out.println("rest_id = " + k + " score on today = " + v));
        return scores;
    }

    public List<Menu> findMenuByRestId(int restId) {
        return mapMenuByDates.get(LocalDate.now()).get(restId);
    }

    public List<Menu> findMenuByRestIdAndDate(LocalDate localDate, int restId) {
        if (!mapMenuByDates.containsKey(localDate)) {
            return null;
        }
        return mapMenuByDates.get(localDate).get(restId);
    }

    public boolean deleteMenuByRestIdAndDate(LocalDate localDate, int restId) {
        if (!mapMenuByDates.containsKey(localDate)) {
            return false;
        }
        return mapMenuByDates.get(localDate).remove(restId) != null;
    }

    public List<Menu> saveMenu(LocalDate localDate, int restId, List<Menu> menuList) {

        if (!mapMenuByDates.containsKey(localDate)) {
            Map<Integer, List<Menu>> map = new HashMap<>();
            mapMenuByDates.put(localDate, map);
        }

        mapMenuByDates.get(localDate).put(restId, menuList);
        List<Menu> result = mapMenuByDates.get(localDate).get(restId);
        return result;
    }

    public Set<Vote> findVotesOnDate(LocalDate localDate) { //discuss it
        return mapVotes.get(localDate);
    }

    public boolean saveVoteSpecial(Vote vote) {
        return mapVotes.get(LocalDate.of(2022, 11, 1)).add(vote); //#1
    }

    public boolean deleteVoteSpecial(Vote vote) {
        Set<Vote> votes = mapVotes.get(LocalDate.of(2022, 11, 1)); //#1
        return votes.remove(vote);
    }

    public Vote findVote(int userId) { //test it
        LocalDate today = LocalDate.of(2022, 11, 01); //#1
        Set<Vote> todayVotes = mapVotes.get(today);
        Vote usersVoteToday = null;
        for (Vote item : todayVotes) {
            if (item.getUserId() == userId) {
                usersVoteToday = item;
            }
        }
        return usersVoteToday;
    }

}
