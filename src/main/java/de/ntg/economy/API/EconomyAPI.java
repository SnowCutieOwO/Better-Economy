package de.ntg.economy.API;

import de.ntg.economy.enums.EconomyResponse;
import de.ntg.economy.events.customEvents.onMoneyChangeEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import de.ntg.economy.main.BetterEconomy;

import java.util.UUID;

public class EconomyAPI {

    public EconomyResponse give(Player player, int amount) {
        if (isPlayerExisting(player.getUniqueId())) {
            int newAmount = BetterEconomy.getInstance().getData().getBalance(player) + amount;
            onMoneyChangeEvent onMoneyChangeEvent = new onMoneyChangeEvent(amount,player.getUniqueId(), newAmount);
            Bukkit.getPluginManager().callEvent(onMoneyChangeEvent);

            if(onMoneyChangeEvent.isCancelled()){
                return EconomyResponse.CANCELLED_BY_EVENT;
            }

            BetterEconomy.getInstance().getData().setBalance(player, onMoneyChangeEvent.getNewAmount());
            return EconomyResponse.SUCCESSFUL;
        } else {
            return EconomyResponse.PLAYER_DOES_NOT_EXISTS;
        }
    }

    public int get(Player player) {
        if (isPlayerExisting(player.getUniqueId())) {
            return BetterEconomy.getInstance().getData().getBalance(player.getUniqueId());
        } else {
            return 0;
        }
    }

    public EconomyResponse remove(Player player, int amount) {
        if (isPlayerExisting(player.getUniqueId())) {
            if (has(player, amount)) {
                int newAmount = BetterEconomy.getInstance().getData().getBalance(player) - amount;
                onMoneyChangeEvent onMoneyChangeEvent = new onMoneyChangeEvent(amount,player.getUniqueId(), newAmount);
                Bukkit.getPluginManager().callEvent(onMoneyChangeEvent);

                if(onMoneyChangeEvent.isCancelled()){
                    return EconomyResponse.CANCELLED_BY_EVENT;
                }

                BetterEconomy.getInstance().getData().setBalance(player, onMoneyChangeEvent.getNewAmount());
                return EconomyResponse.SUCCESSFUL;
            } else {
                return EconomyResponse.PLAYER_DOES_NOT_HAVE_ENOUGH_MONEY;
            }

        } else {
            return EconomyResponse.PLAYER_DOES_NOT_EXISTS;
        }
    }

    public EconomyResponse set(Player player, int amount) {
        if (isPlayerExisting(player.getUniqueId())) {
            onMoneyChangeEvent onMoneyChangeEvent = new onMoneyChangeEvent(amount,player.getUniqueId(), amount);
            Bukkit.getPluginManager().callEvent(onMoneyChangeEvent);

            if(onMoneyChangeEvent.isCancelled()){
                return EconomyResponse.CANCELLED_BY_EVENT;
            }

            BetterEconomy.getInstance().getData().setBalance(player, onMoneyChangeEvent.getNewAmount());
            return EconomyResponse.SUCCESSFUL;
        } else {
            return EconomyResponse.PLAYER_DOES_NOT_EXISTS;
        }
    }

    public EconomyResponse pay(Player player, Player target, int amount) {
        if (isPlayerExisting(player.getUniqueId()) && isPlayerExisting(target.getUniqueId())) {
            if (has(player, amount)) {
                remove(player, amount);
                give(target, amount);
                return EconomyResponse.SUCCESSFUL;
            } else {
                return EconomyResponse.PLAYER_DOES_NOT_HAVE_ENOUGH_MONEY;
            }

        } else {
            return EconomyResponse.PLAYER_DOES_NOT_EXISTS;
        }
    }

    public boolean has(Player player, int amount) {
        if (isPlayerExisting(player.getUniqueId())) {
            return BetterEconomy.getInstance().getData().getBalance(player) >= amount;
        } else {
            return false;
        }

    }

    public EconomyResponse give(UUID uuid, int amount) {
        if (isPlayerExisting(uuid)) {
            int newAmount = BetterEconomy.getInstance().getData().getBalance(uuid) + amount;
            onMoneyChangeEvent onMoneyChangeEvent = new onMoneyChangeEvent(amount,uuid, newAmount);
            Bukkit.getPluginManager().callEvent(onMoneyChangeEvent);

            if(onMoneyChangeEvent.isCancelled()){
                return EconomyResponse.CANCELLED_BY_EVENT;
            }

            BetterEconomy.getInstance().getData().setBalance(uuid, onMoneyChangeEvent.getNewAmount());
            return EconomyResponse.SUCCESSFUL;
        } else {
            return EconomyResponse.PLAYER_DOES_NOT_EXISTS;
        }
    }

    public EconomyResponse remove(UUID uuid, int amount) {
        if (isPlayerExisting(uuid)) {
            if (has(uuid, amount)) {
                int newAmount = BetterEconomy.getInstance().getData().getBalance(uuid) - amount;
                onMoneyChangeEvent onMoneyChangeEvent = new onMoneyChangeEvent(amount,uuid, newAmount);
                Bukkit.getPluginManager().callEvent(onMoneyChangeEvent);

                if(onMoneyChangeEvent.isCancelled()){
                    return EconomyResponse.CANCELLED_BY_EVENT;
                }

                BetterEconomy.getInstance().getData().setBalance(uuid, onMoneyChangeEvent.getNewAmount());
                return EconomyResponse.SUCCESSFUL;
            } else {
                return EconomyResponse.PLAYER_DOES_NOT_HAVE_ENOUGH_MONEY;
            }
        } else {
            return EconomyResponse.PLAYER_DOES_NOT_EXISTS;
        }
    }

    public EconomyResponse set(UUID uuid, int amount) {
        if (isPlayerExisting(uuid)) {
            onMoneyChangeEvent onMoneyChangeEvent = new onMoneyChangeEvent(amount,uuid, amount);
            Bukkit.getPluginManager().callEvent(onMoneyChangeEvent);

            if(onMoneyChangeEvent.isCancelled()){
                return EconomyResponse.CANCELLED_BY_EVENT;
            }

            BetterEconomy.getInstance().getData().setBalance(uuid, onMoneyChangeEvent.getNewAmount());
            return EconomyResponse.SUCCESSFUL;
        } else {
            return EconomyResponse.PLAYER_DOES_NOT_EXISTS;
        }
    }

    public EconomyResponse pay(UUID uuidPlayer, UUID uuidTarget, int amount) {
        if (isPlayerExisting(uuidPlayer) && isPlayerExisting(uuidTarget)) {
            if (has(uuidPlayer, amount)) {
                remove(uuidPlayer, amount);
                give(uuidTarget, amount);
                return EconomyResponse.SUCCESSFUL;
            } else {
                return EconomyResponse.PLAYER_DOES_NOT_HAVE_ENOUGH_MONEY;
            }

        } else {
            return EconomyResponse.PLAYER_DOES_NOT_EXISTS;
        }
    }

    public boolean has(UUID uuid, int amount) {
        if (isPlayerExisting(uuid)) {
            return BetterEconomy.getInstance().getData().getBalance(uuid) >= amount;
        } else {
            return false;
        }

    }

    public int get(UUID uuid) {
        if (isPlayerExisting(uuid)) {
            return BetterEconomy.getInstance().getData().getBalance(uuid);
        } else {
            return 0;
        }
    }

    public boolean isPlayerExisting(UUID uuid) {
        return BetterEconomy.getInstance().getData().isPlayerExisting(uuid);
    }
}
